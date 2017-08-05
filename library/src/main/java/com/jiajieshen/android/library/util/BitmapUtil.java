package com.jiajieshen.android.library.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public final class BitmapUtil {

    private BitmapUtil() {
        throw new UnsupportedOperationException();
    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with <code>inJustDecodeBounds = true</code> to check dimensions.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (reqWidth == 0 && reqHeight == 0) {
            return inSampleSize;
        }

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both height and width larger than the requested height and
            // width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * center crop to target size.
     */
    public static Bitmap centerCropBitmap(Bitmap srcBmp, int destSize) {
        int srcWidth = srcBmp.getWidth();
        int srcHeight = srcBmp.getHeight();
        if (srcWidth >= srcHeight) {
            destSize = destSize <= srcHeight ? destSize : srcHeight;
        } else {
            destSize = destSize <= srcWidth ? destSize : srcWidth;
        }
        return Bitmap.createBitmap(
                srcBmp,
                srcWidth / 2 - destSize / 2,
                srcHeight / 2 - destSize / 2,
                destSize,
                destSize
        );
    }

    /**
     * Bitmap 宽高缩放
     */
    public static Bitmap scaledBitmap(Bitmap source, float destWidth, float destHeight) {
        int srcWidth = source.getWidth();
        int srcHeight = source.getHeight();
        float scaleX = (float) destWidth / srcWidth;// 要强制转换
        float scaleY = (float) destHeight / srcHeight;
        float scale = Math.min(scaleX, scaleY);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        return Bitmap.createBitmap(source, 0, 0, srcWidth, srcHeight, matrix, true);
    }

    public static Bitmap decodeBitmapFromPath(String srcPath, int reqWidth, int reqHeight) {
        // First decode with <code>inJustDecodeBounds = true</code> to check dimensions.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(srcPath, options);
    }

    /**
     * Bitmap 质量压缩（Bitmap.CompressFormat.JPEG）
     */
    public static Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        //<code>quality = 100</code>表示不压缩，压缩后的数据存放到baos
        for (int quality = 90; quality > 50; quality = quality - 10) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            if (baos.toByteArray().length / 1024 <= 150) {// 循环判断如果压缩后图片是否小于100kb,小于则退出压缩
                break;
            }
            baos.reset();//重置 baos,继续压缩
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * Bitmap 质量压缩（Bitmap.CompressFormat.JPEG）
     */
    public static byte[] compressBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //<code>quality = 100</code>表示不压缩，压缩后的数据存放到baos
        for (int quality = 100; quality > 50; quality = quality - 10) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            if (baos.toByteArray().length / 1024 <= 150) {// 循环判断如果压缩后图片是否小于150kb,小于则退出压缩
                break;
            }
            baos.reset();//重置baos,继续压缩
        }
        return baos.toByteArray();
    }

}
