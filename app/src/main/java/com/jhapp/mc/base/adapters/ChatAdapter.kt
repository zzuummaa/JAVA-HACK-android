package com.jhapp.mc.base.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jhapp.mc.R
import com.jhapp.mc.api.models.ChatMessage
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

class ChatAdapter: BaseSearchAdapter<ChatMessage>() {

    val de = DateEntity()

    fun setDataWithDiffResult(data: MutableList<ChatMessage>) {
        mDataList = data
    }

    override fun getItemViewType(position: Int): Int {
        return if (mDataList[position].bot) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessage> {
        if (viewType == 0) {
            return LeftViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_left, parent, false))
        } else {
            return LeftViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_right, parent, false))

        }
    }

    private inner class LeftViewHolder(v: View): BaseSearchAdapter.BaseViewHolder<ChatMessage>(v) {
        override fun bind(model: ChatMessage) {
            with(itemView) {
                if (model.body.contains("ref://")) {
                    findViewById<TextView>(R.id.message).text = "Перейти к предложению ->"
                } else {
                    findViewById<TextView>(R.id.message).text = model.body
                }

                val date = de.parseDate(model.date) ?: Date()

                findViewById<TextView>(R.id.tv_time).text = de.getHoursAndMinString(date)
            }
        }
    }
}

class DateEntity {

    companion object {
        private const val FIRST_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss'Z'"
        private const val SECOND_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val THIRD_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        private const val PREETY_TIME_PATTERN = "HH:mm:ss"
        private const val PREETY_DATE_PATTERN = "dd.MM.yyyy"
        private const val API_DATE_PATTERN = "yyyy.MM.dd"
        private const val PREETY_DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss"
        private const val P2P_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        private const val P2P_SIMPLE_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

        private const val PREETY_DATE_TIME_WITHOUT_SEC_PATTERN = "dd.MM.yyyy HH:mm"


        private const val HOUR_AND_MIN_PATTER = "HH:mm"
        private const val DAY_AND_MONTH_PATTERN = "dd.MM"
    }

    private val firstDateFormatter = SimpleDateFormat(
        FIRST_DATE_PATTERN).apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val secondDatePattern = SimpleDateFormat(
        SECOND_DATE_PATTERN).apply { timeZone = TimeZone.getTimeZone("UTC") }
    private val thirdDatePattern = SimpleDateFormat(
        THIRD_DATE_PATTERN).apply { timeZone = TimeZone.getTimeZone("UTC") }

    private val firstDateFormatterLocale = SimpleDateFormat(
        FIRST_DATE_PATTERN)//.apply{ timeZone = TimeZone.getDefault()}
    private val secondDatePatternLocale = SimpleDateFormat(
        SECOND_DATE_PATTERN)//.apply{ timeZone = TimeZone.getDefault()}
    private val thirdDatePatternLocale = SimpleDateFormat(
        THIRD_DATE_PATTERN)//.apply{ timeZone = TimeZone.getDefault()}
    private val preetyFormatLocale = SimpleDateFormat(
        PREETY_TIME_PATTERN)//.apply{ timeZone = TimeZone.getDefault()}
    private val preetyDateFormatLocale = SimpleDateFormat(PREETY_DATE_PATTERN)//.apply{ timeZone = TimeZone.getDefault()}
    private val apiDateFormatterLocale = SimpleDateFormat(API_DATE_PATTERN)
    private val dateTimeFormatterLocale = SimpleDateFormat(PREETY_DATE_TIME_PATTERN)
    private val p2pTimeFormatter = SimpleDateFormat(P2P_DATE_PATTERN)
    private val p2pSimpleDateFormatter = SimpleDateFormat(P2P_SIMPLE_DATE_PATTERN)
    private val hoursAndMinFormat = SimpleDateFormat(HOUR_AND_MIN_PATTER)
    val preetyDateTimeWIthoutSec = SimpleDateFormat(PREETY_DATE_TIME_WITHOUT_SEC_PATTERN)

    private val dayAndMnthFormat = SimpleDateFormat(DAY_AND_MONTH_PATTERN)

    fun parseTime(time: String) = try {
        preetyDateFormatLocale.parse(time)
    } catch (e: Exception) {
        try {
            hoursAndMinFormat.parse(time)
        } catch (e: Exception) {
            null
        }
    }

    fun parseDate(date: String) = try {
        firstDateFormatter.parse(date)
    } catch (ex: ParseException) {
        try {
            secondDatePattern.parse(date)
        } catch (ex: ParseException) {
            try {
                thirdDatePattern.parse(date)
            } catch (ex: ParseException) {
                try {
                    p2pTimeFormatter.parse(date)
                } catch (ex: ParseException) {
                    try {
                        p2pSimpleDateFormatter.parse(date)
                    } catch (ex: ParseException) {
                        null
                    }
                }
            }
        }
    }

//    fun getTimeZone(date: Date): String {
//
//    }

    fun nowToFirstDayPattern() = firstDateFormatter.format(Date())

    fun getPreetyString(date: Date?): String {
        return preetyFormatLocale.format(date ?: Date())
    }

    fun getPreetyDateString(date: Date): String {
        return preetyDateFormatLocale.format(date)
    }

    fun getHoursAndMinString(date: Date): String {
        return hoursAndMinFormat.format(date)
    }

    fun getApiDateString(date: Date) = apiDateFormatterLocale.format(date)

    fun getDateTimeString(date: Date) = dateTimeFormatterLocale.format(date)

    fun getDayAndMnthFormat(date: Date) = dayAndMnthFormat.format(date)

    fun daysFromNow(date: Date): Long {
        val between = System.currentTimeMillis() - date.time
        return TimeUnit.DAYS.convert(between, TimeUnit.MILLISECONDS)
    }

    fun isToday(date: Date)
            = System.currentTimeMillis() % TimeUnit.DAYS.toMillis(1) > date.time % TimeUnit.DAYS.toMillis(1)
            && daysFromNow(date) == 0L

    fun isNotToday(date: Date) = !isToday(date)

}