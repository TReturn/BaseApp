package com.example.lib_main.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import me.hgj.jetpackmvvm.ext.nav
import java.io.IOException


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
                if (it.resultCode == RESULT_OK) {
                    val uri = it.data?.getBundleExtra("RESULT")?.getString("PICTURE_URI")
                    // 这里获取到对应相片，如果用于显示，建议进行相应压缩处理
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(mActivity.contentResolver, Uri.parse(uri))
                    GlideUtils.loadImageProtist(mActivity, bitmap, mDatabind.ivOriginal)
                }
            }
    }

    inner class ProxyClick {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun toTakePhoto() {
            //拍照、存储权限请求
            PermissionX.init(mActivity)
                .permissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
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
                        startActivityLauncher.launch(intent)
                    } else {
                        Toaster.show(getString(R.string.main_scan_permissions_fail))
                    }
                }
        }

    }

}