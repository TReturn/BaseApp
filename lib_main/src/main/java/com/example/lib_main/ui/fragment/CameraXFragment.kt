package com.example.lib_main.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.utils.image.BigImageUtils
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentCameraXBinding
import com.example.lib_main.viewmodel.CameraXViewModel
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.hjq.toast.Toaster
import com.permissionx.guolindev.PermissionX
import com.zyt.lib_camera.ui.activity.CameraActivity
import com.zyt.lib_camera.utils.CompressedUtils
import me.hgj.jetpackmvvm.ext.nav

/**
 * @CreateDate: 2023/8/29 17:35
 * @Author: 青柠
 * @Description: CameraX调用示例
 */
class CameraXFragment : BaseFragment<CameraXViewModel, FragmentCameraXBinding>() {

    private lateinit var startCameraLauncher: ActivityResultLauncher<Intent>

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

        startCameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK && it.data != null) {
                    //拍摄原图的URI，可转Bitmap
                    val uri = it.data?.getBundleExtra("RESULT")?.getString("PICTURE_URI")
                    //压缩后的图片路径
                    val path = it.data?.getBundleExtra("RESULT")?.getString("PICTURE_PATH")

                    mViewModel.imageUrl.value = path.toString()
                    mViewModel.originalPic.value = CompressedUtils.getOriginalSize()
                    mViewModel.compressPic.value = CompressedUtils.getCompressSize()
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
                        startCameraLauncher.launch(intent)
                    } else {
                        Toaster.show(getString(R.string.main_scan_permissions_fail))
                    }
                }
        }

        fun toOpenPhoto() {
            if (mViewModel.imageUrl.value.isNullOrEmpty()) return
            BigImageUtils.show(mActivity, mViewModel.imageUrl.value.toString())
        }

    }

}