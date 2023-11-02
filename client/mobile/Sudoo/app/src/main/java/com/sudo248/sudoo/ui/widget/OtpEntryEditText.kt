package com.sudo248.sudoo.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.sudo248.sudoo.R


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 10:52 - 09/03/2023
 */
class OtpEntryEditText
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet
) : AppCompatEditText(context, attrs) {

    private var space = 24f
    private var charSize = 0f
    private var numChars = 6
    private var strokeWidth = 1.5f
    private var radius = 10f
    private val recPaint: Paint
    private val backgroundPaint: Paint

    private val states = arrayOf(
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(android.R.attr.state_focused),
        intArrayOf(-android.R.attr.state_focused)
    )

    private val colors = intArrayOf(
        context.getColor(R.color.primaryColor),
        context.getColor(R.color.primaryColor),
        Color.GRAY
    )

    private val colorStates = ColorStateList(states, colors)

    private var onClickListener: OnClickListener? = null

    private var onFullFillListener: ((String) -> Unit)? = null

    init {
        val multi = context.resources.displayMetrics.density
        strokeWidth *= multi
        recPaint = Paint(paint).apply {
            style = Paint.Style.STROKE
            strokeWidth = this@OtpEntryEditText.strokeWidth
        }

        backgroundPaint = Paint(paint).apply {
            style = Paint.Style.FILL
        }
        if (!isInEditMode) {
            val outValue = TypedValue()
            context.theme.resolveAttribute(
                androidx.appcompat.R.attr.colorControlActivated,
                outValue,
                true
            )

            val colorActivated = outValue.data
            colors[0] = colorActivated

            context.theme.resolveAttribute(
                androidx.appcompat.R.attr.colorPrimaryDark,
                outValue,
                true
            )
            val colorDark = outValue.data
            colors[1] = colorDark

            context.theme.resolveAttribute(
                androidx.appcompat.R.attr.colorControlHighlight,
                outValue,
                true
            )
            val colorHighlight = outValue.data
            colors[2] = colorHighlight
        }

        setBackgroundColor(0)
        space *= multi
        val maxLength =
            attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLength", 6)
        numChars = maxLength

        val background =
            attrs.getAttributeResourceValue(
                "http://schemas.android.com/apk/res/android",
                "background",
                Color.WHITE
            )

        backgroundPaint.color = background

        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.OtpEntryEditText, 0, 0)
        try {
            radius = attributes.getFloat(R.styleable.OtpEntryEditText_connerRadius, 0f)
        } finally {
            attributes.recycle()
        }
        // disable copy
        super.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return false;
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

            }
        })

        super.setOnClickListener {
            setSelection(text?.length ?: 0)
            onClickListener?.onClick(it)
        }

        super.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length == maxLength) {
                    onFullFillListener?.invoke(s.toString())
                }
            }
        })

    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback?) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val availableWidth = width - paddingRight - paddingLeft
        charSize = if (space < 0) {
            (availableWidth / (numChars * 2f - 1))
        } else {
            (availableWidth - (space * (numChars - 1))) / numChars
        }
        val availableHeight = charSize + strokeWidth * 2

        var startX = paddingLeft.toFloat()

        // Text width
        val textLength = text?.length ?: 0
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(text, 0, textLength, textWidths)

        for (i in 0..numChars) {
            canvas.drawRoundRect(
                RectF(startX, strokeWidth, startX + charSize, availableHeight),
                radius,
                radius,
                backgroundPaint
            )
            updateColorStroke(i <= textLength)
            canvas.drawRoundRect(
                RectF(startX, strokeWidth, startX + charSize, availableHeight),
                radius,
                radius,
                recPaint
            )
            if (textLength > i) {
                val middle = startX + charSize / 2
                canvas.drawText(
                    text.toString(),
                    i,
                    i + 1,
                    middle - textWidths[0] / 2,
                    availableHeight - (availableHeight - textSize) / 2,
                    paint
                )
            }

            startX += if (space < 0) {
                charSize * 2
            } else {
                charSize + space
            }
        }

    }

    private fun getColorForState(vararg states: Int): Int {
        return colorStates.getColorForState(states, Color.GRAY)
    }

    /**
     * @param next Is the current char the next character to be input?
     */
    private fun updateColorStroke(next: Boolean) {
        if (isFocused && next) {
            recPaint.color = context.getColor(R.color.primaryColor)
        } else {
            recPaint.color = Color.GRAY
        }
    }

    fun setConnerRadius(radius: Float) {
        this.radius = radius
        invalidate()
    }

    fun setOnFulFillListener(listener: (String) -> Unit) {
        this.onFullFillListener = listener
    }
}