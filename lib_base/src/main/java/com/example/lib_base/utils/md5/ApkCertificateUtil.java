package com.example.lib_base.utils.md5;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @CreateDate: 2023/8/31 18:19
 * @Author: 青柠
 * @Description: 获取应用的MD5
 */

public class ApkCertificateUtil {
    public static String getApkCertificateMD5(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES
            );
            Signature[] signatures = packageInfo.signatures;
            if (signatures.length > 0) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(signatures[0].toByteArray());
                byte[] md5 = md.digest();
                StringBuilder sb = new StringBuilder();
                for (byte b : md5) {
                    sb.append(String.format("%02X", b));
                }
                return sb.toString();
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
