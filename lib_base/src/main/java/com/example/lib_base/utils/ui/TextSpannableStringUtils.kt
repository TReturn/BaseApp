package com.example.lib_base.utils.ui


import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.widget.TextView
import androidx.annotation.DrawableRes


/**
 * Created by yanshirong on 2021/9/30.
 * E-Mail:yanshirong@shanda.com
 * Description: text文本 添加某部分颜色改变 并可点击
 */
object TextSpannableStringUtils {

    /**
     * 使用SpannableString设置样式——字体颜色
     * 修改字体颜色
     *
     * @param content 改变的文本内容
     * @param color   "#009ad6"
     * @param start   开始文字的位置 坐标从 0开始
     * @param end     改变结束的位置 ，并不包括这个位置。
     * @return
     */
    fun textColor(content: String?, color: String?, start: Int, end: Int): SpannableString? {
        val spannableString = SpannableString(content)
        val colorSpan = ForegroundColorSpan(Color.parseColor(color))
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        // ((TextView)findViewById(R.id.mode1)).setText(spannableString);
        return spannableString
    }


    /**
     * 使用SpannableStringBuilder设置样式——字体颜色
     * SpannableStringBuilder
     * 修改字体颜色
     *
     * @param content 改变的文本内容
     * @param color   #009ad6
     * @param start   开始文字的位置 坐标从 0开始
     * @param end     改变结束的位置 ，并不包括这个位置。
     * @return
     */
    fun textColor02(
        content: String?,
        color: String?,
        start: Int,
        end: Int
    ): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        // spannableString.append("已经开始暴走了");
        val colorSpan = ForegroundColorSpan(Color.parseColor(color))
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        // ((TextView)findViewById(R.id.mode2)).setText(spannableString);
        return spannableString
    }

    /**
     * 使用SpannableStringBuilder设置样式——背景颜色
     * 设置背景颜色 --文本内容字体颜色不改变
     * 使用BackgroundColorSpan设置背景颜色。
     *
     * @param content 改变的文本内容
     * @param color   #009ad6
     * @param start   开始文字的位置  坐标从 0开始
     * @param end     改变结束的位置 ，并不包括这个位置。
     * @return
     */
    fun textBackgroundColor(
        content: String?,
        color: String?,
        start: Int,
        end: Int
    ): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        val bgColorSpan = BackgroundColorSpan(Color.parseColor(color))
        spannableString.setSpan(bgColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //((TextView)findViewById(R.id.mode3)).setText(spannableString);
        return spannableString
    }

    /**
     * 使用SpannableStringBuilder设置样式——字体大小
     * 设置字体大小
     * 使用AbsoluteSizeSpan设置字体大小。
     *
     * @param content  改变的文本内容
     * @param textSize 设置字体大小  代码中 设置字体的大小是 px 如需要设置dp 请先把dp转换为px
     * @param start    开始文字的位置  坐标从 0开始
     * @param end      改变结束的位置 ，并不包括这个位置。
     * @return
     */
    fun textSize(content: String?, textSize: Int, start: Int, end: Int): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        val absoluteSizeSpan = AbsoluteSizeSpan(textSize)
        spannableString.setSpan(absoluteSizeSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        // ((TextView)findViewById(R.id.mode4)).setText(spannableString);
        return spannableString
    }


    /**
     * 使用SpannableStringBuilder设置样式——粗体\斜体
     * @param content   改变的文本内容
     * @param start    开始文字的位置  坐标从 0开始
     * @param end      改变结束的位置 ，并不包括这个位置。
     * @param Tepy   字体样式  1 :粗体  2:斜体 3:粗斜体
     * @return
     */
    fun textSetStyle(content: String?, start: Int, end: Int, Tepy: Int): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        // TODO setSpan可多次使用 也就是一段字符串上都可以使用  粗体,斜体,粗斜体 如有需要请自行配置使用
        /* StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);//粗体
    spannableString.setSpan(styleSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    StyleSpan styleSpan2 = new StyleSpan(Typeface.ITALIC);//斜体
    spannableString.setSpan(styleSpan2, 3, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    StyleSpan styleSpan3 = new StyleSpan(Typeface.BOLD_ITALIC);//粗斜体
    spannableString.setSpan(styleSpan3, 6, 9, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    ((TextView)findViewById(R.id.mode5)).setText(spannableString);
    */
        var styleSpan: StyleSpan? = null
        when (Tepy) {
            Typeface.BOLD -> {
                styleSpan = StyleSpan(Typeface.BOLD) //粗体
                spannableString.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }

            Typeface.ITALIC -> {
                styleSpan = StyleSpan(Typeface.ITALIC) //斜体
                spannableString.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }

            Typeface.BOLD_ITALIC -> {
                styleSpan = StyleSpan(Typeface.BOLD_ITALIC) //粗斜体
                spannableString.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }

            else -> {
                styleSpan = StyleSpan(Typeface.BOLD) //粗体
                spannableString.setSpan(styleSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            }
        }
        return spannableString
    }


    /**
     * 使用SpannableStringBuilder设置样式——删除线   文字中间带横线
     *
     * @param content   改变的文本内容
     * @param start    开始文字的位置  坐标从 0开始
     * @param end      改变结束的位置 ，并不包括这个位置。
     */
    fun textDeleteLine(content: String?, start: Int, end: Int): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        val strikethroughSpan = StrikethroughSpan()
        spannableString.setSpan(strikethroughSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        // ((TextView) findViewById(R.id.mode6)).setText(spannableString);
        return spannableString
    }


    /**
     * 使用SpannableStringBuilder设置样式——下划线
     * @param content
     * @param start
     * @param end
     * @return
     */
    fun textUnderline(content: String?, start: Int, end: Int): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        val underlineSpan = UnderlineSpan()
        spannableString.setSpan(underlineSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //((TextView) findViewById(R.id.mode7)).setText(spannableString);
        return spannableString
    }


    /**
     * 使用SpannableStringBuilder设置样式——图片
     * @param context  上下文
     * @param content   改变文本内容
     * @param start
     * @param end
     * @param mipmapId 图片
     * @param isWidthHeight 是否需要测量宽高
     * @return
     */
    fun textImage(
        context: Context, content: String?, start: Int, end: Int,
        @DrawableRes mipmapId: Int, isWidthHeight: Boolean
    ): SpannableStringBuilder? {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        var imageSpan: ImageSpan? = null
        if (isWidthHeight) { // 需要根据 测量图片的 宽高不
            val drawable: Drawable = context.getResources().getDrawable(mipmapId)
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight())
            imageSpan = ImageSpan(drawable)
        } else {
            imageSpan = ImageSpan(context, mipmapId)
        }
        //将index为6、7的字符用图片替代
        spannableString.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        // ((TextView) findViewById(R.id.mode8)).setText(spannableString);
        return spannableString
    }


    /**
     * 使用SpannableStringBuilder设置点击事件
     * @param textView
     * @param content  文本内容
     * @param start    开始文字的位置  坐标从 0开始
     * @param end      改变结束的位置 ，并不包括这个位置。
     * @param clickableSpan   文字点击的回调
     *
     * textView.setText(spannableString);
     * textView.setMovementMethod(LinkMovementMethod.getInstance()); 代码中指定index为 start--end 的字符都成了可点击的文本，其他区域还是不可点击的。
     */
    fun textClickable(
        textView: TextView,
        content: String?,
        start: Int,
        end: Int,
        clickableSpan: ClickableSpan?
    ) {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        textView.setText(spannableString)
        textView.setMovementMethod(LinkMovementMethod.getInstance())
    }


    /**
     * 使用SpannableStringBuilder设置 文本颜色 和 点击事件
     * @param textView
     * @param content
     * @param start
     * @param end
     * @param start2
     * @param end2
     * @param color
     * @param clickableSpan
     * @param clickableSpan2
     */
    fun textColorAndClickable(
        textView: TextView, content: String?, start: Int, end: Int, start2: Int, end2: Int,
        color: String?, clickableSpan: ClickableSpan?, clickableSpan2: ClickableSpan?
    ) {
        val spannableString = SpannableStringBuilder()
        spannableString.append(content)
        //文字1颜色
        val colorSpan = ForegroundColorSpan(Color.parseColor(color))
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //文字2颜色
        val colorSpan2 = ForegroundColorSpan(Color.parseColor(color))
        spannableString.setSpan(colorSpan2, start2, end2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)


        //点击1数据
        spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        //点击2数据
        spannableString.setSpan(clickableSpan2, start2, end2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

}