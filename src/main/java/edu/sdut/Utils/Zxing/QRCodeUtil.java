package edu.sdut.Utils.Zxing;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/19 14:28
 */
public class QRCodeUtil
{

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成二维码图片
     * @param content
     * @param width
     * @param height
     * @param hints
     * @return
     * @throws Exception
     */

    private static BufferedImage getQrCodeImage(String content, int width, int height, Hashtable hints) throws Exception
    {
        // 使用zxing生成二维码图片数据类BitMatrix
        QRCodeWriter writer = new QRCodeWriter();

        if (hints == null)
        {
            //默认就行
            hints = new Hashtable();
            // 指定编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 指定纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        }
        //编码信息，生成二维码
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        // 修改默认生成的二维码白边
        BitMatrix newMatrix = updateFrameWidth(matrix, 0);

        // 缩放图片（因为裁剪了空白的边框）
        BufferedImage bi = toBufferedImage(newMatrix);

        // 计算x轴y轴缩放比例
        double sx = (double)width / bi.getWidth();
        double sy = (double)height / bi.getHeight();

        BufferedImage zoomImage = zoomInImage(bi, sx, sy);

        return zoomImage;
    }

    /**
     * 将google zxing生成的二维码数据结构BitMatrix的内容写入图片缓冲区BufferedImage
     * @param matrix
     * @return
     */

    public static BufferedImage toBufferedImage(BitMatrix matrix)
    {

        int width = matrix.getWidth();
        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }

        return image;
    }

    /**
     *
     * 获取二维码字节流
     * @param content
     * @param width
     * @param height
     * @return
     * @throws Exception
     */

    public static byte[] getQrCodeImageBytes(String content, int width, int height, Hashtable hints) throws Exception
    {

        // 二维码输出质量，0:L(低), 1:M(中等), 2:Q, 3:H(高)
        int level = 3;


        // 生成二维码图片
        BufferedImage zoomImage = getQrCodeImage(content, width, height, hints);

        // 转为二进制字节
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        if (!ImageIO.write(zoomImage, "png", out))
        {
            throw new IOException("Could not write an image of format png");
        }

        byte[] binaryData = out.toByteArray();

        return binaryData;
    }

    /**
     * 进行Base64编码
     * @param content
     * @param width
     * @param height
     * @param hints
     * @return
     * @throws Exception
     */

    public static String getQrCodeImageBase64String(String content, int width, int height, Hashtable hints) throws Exception
    {
        // 生成二维码图片的二进制字节
        byte[] binaryData = getQrCodeImageBytes(content, width, height, hints);

        // 将二进制字节进行Base64编码
        String ret = new String(Base64.encodeBase64(binaryData), "UTF-8");

        return ret;
    }

    /**
     * 指定缩放比例，进行图片缩放
     * @param originalImage
     * @param sx
     * @param sy
     * @return
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, double sx, double sy)
    {
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(sx, sy), null);
        BufferedImage newImage = op.filter(originalImage, null);

        return newImage;
    }

    /**
     * 自定义修改二维码的白边宽度
     * @param matrix
     * @param margin
     * @return
     */
    public static BitMatrix updateFrameWidth(BitMatrix matrix, int margin)
    {
        int tempM = margin * 2;

        // 二维码的实际尺寸
        int[] whitesTopLeft = getFrameWidthOfTopLeft(matrix);
        int[] whitesBottomRight = getFrameWidthOfBottomRight(matrix);

        int whiteWidth = whitesTopLeft[0] + whitesBottomRight[0];
        int whiteHeight = whitesTopLeft[1] + whitesBottomRight[1];

        int height = matrix.getHeight() - whiteWidth + tempM;
        int width = matrix.getWidth() - whiteHeight + tempM;

        // 按照自定义边框生成新的BitMatrix
        BitMatrix resMatrix = new BitMatrix(width, height);
        resMatrix.clear();

        // 循环，将二维码图案绘制到新的bitMatrix中
        for (int i = margin; i < width - margin; i++)
        {
            for (int j = margin; j < height - margin; j++)
            {
                if (matrix.get(i - margin + whitesTopLeft[0], j - margin + whitesTopLeft[1]))
                {
                    // 新的二维码
                    resMatrix.set(i, j);
                }
            }
        }

        return resMatrix;
    }

    /**
     * 二维码左上角白边的尺寸
     * @param matrix
     * @return
     */
    private static int[] getFrameWidthOfTopLeft(BitMatrix matrix)
    {
        // 二维码的尺寸
        int height = matrix.getHeight();
        int width = matrix.getWidth();

        // 循环，遇到第一个黑点，就返回
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (matrix.get(i, j) == true)
                {
                    int[] ret = {i, j};
                    return ret;
                }
            }
        }

        int[] ret = {0, 0};
        return ret;
    }

    /**
     * 二维码右下角白边的尺寸
     * @param matrix
     * @return
     */
    private static int[] getFrameWidthOfBottomRight(BitMatrix matrix)
    {
        // 二维码的尺寸
        int height = matrix.getHeight();
        int width = matrix.getWidth();

        // 备注：不能直接查找右下角的第一个黑点，而必须分别查找右上角、左下角的第一个黑点，然后返回右下角坐标
        int right = -1;

        // 从右上角开始循环，遇到第一个黑点，就得到右边空白宽度
        for (int i = width; i > 0; i--)
        {
            for (int j = 0; j < height; j++)
            {
                if (matrix.get(i - 1, j) == true)
                {
                    right = width - i;
                    break;
                }
            }

            if (right >= 0)
            {
                break;
            }
        }

        // 从左下角开始循环，遇到第一个黑点，就得到下边空白宽度，返回
        for (int i = 0; i < width; i++)
        {
            for (int j = height; j > 0; j--)
            {
                if (matrix.get(i, j - 1) == true)
                {
                    int[] ret = {right, height - j};
                    return ret;
                }
            }
        }

        int[] ret = {0, 0};
        return ret;
    }

}