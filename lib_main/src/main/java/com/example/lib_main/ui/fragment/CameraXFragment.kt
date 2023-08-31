package com.example.lib_main.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.utils.image.GlideUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentCameraXBinding
import com.example.lib_main.viewmodel.CameraXViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.hjq.toast.Toaster
import com.permissionx.guolindev.PermissionX
import com.zyt.lib_camera.ui.activity.CameraActivity
import com.zyt.lib_camera.utils.CompressedUtils
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate: 2023/8/29 17:35
 * @Author: 青柠
 * @Description: CameraX调用示例
 */
class CameraXFragment : BaseFragment<CameraXViewModel, FragmentCameraXBinding>() {

    private lateinit var startActivityLauncher: ActivityResultLauncher<Intent>

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.vm = mViewModel
        setTranslucent(mDatabind.flTranslucent)

        mDatabind.include.titleBar.title = getString(R.string.main_type_camera)
        mDatabind.include.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar) {
                nav().navigateUp()
            }
        })

        startActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK && it.data != null) {
                    val uri = it.data?.getBundleExtra("RESULT")?.getString("PICTURE_URI")
                    // 这里获取到对应相片，如果用于显示，建议进行相应压缩处理
                    val bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(mActivity.contentResolver, Uri.parse(uri))
                    } else {
                        val source = ImageDecoder.createSource(
                            mActivity.contentResolver,
                            Uri.parse(uri)
                        )
                        ImageDecoder.decodeBitmap(source)
                    }

                    //加载压缩图
                    lifecycleScope.launch {
                        val file = CompressedUtils.compressed(mActivity, bitmap)
                        GlideUtils.loadRoundImage(mActivity, file, mDatabind.ivCompress, 12F)

                        mViewModel.originalPic.value = CompressedUtils.getOriginalSize()
                        mViewModel.compressPic.value = CompressedUtils.getCompressSize()
                    }

                }
            }
    }


    inner class ProxyClick {
        fun toTakePhoto() {
            //拍照、存储权限请求

            val permission: MutableList<String> = arrayListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            PermissionX.init(mActivity)
                .permissions(permission)
                .explainReasonBeforeRequest()
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(
                        deniedList, getString(R.string.main_scan_permissions_tips),
                        getString(R.string.main_ok), getString(R.string.main_cancel)
                    )
                }

                .request { allGranted, _, _ ->
                    if (allGranted) {
                        val intent = Intent(requireActivity(), CameraActivity::class.java)
                        //可选择直接打开图库
                        intent.putExtra("IS_OPEN_GALLERY", false)
                        startActivityLauncher.launch(intent)
                    } else {
                        Toaster.show(getString(R.string.main_scan_permissions_fail))
                    }
                }
        }

    }

}