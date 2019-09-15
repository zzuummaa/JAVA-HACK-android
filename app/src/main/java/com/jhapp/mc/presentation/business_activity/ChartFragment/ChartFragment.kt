package com.jhapp.mc.presentation.business_activity.ChartFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.jhapp.mc.R
import com.jhapp.mc.api.models.Chart
import kotlinx.android.synthetic.main.fragment_chart.*

class ChartFragment: Fragment() {

    companion object {
        private const val TITLE_EXTRA = "title_extra"
        private const val CHART_EXTRA = "chart_extra"
        private const val TYPE_EXTRA = "type_extra"

        val instance = {
            chart: Chart, title: String, type: Int ->
            ChartFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CHART_EXTRA, chart)
                    putString(TITLE_EXTRA, title)
                    putInt(TYPE_EXTRA, type)
                }
            }
        }
    }

    private val chartData by lazy { arguments!!.getParcelable<Chart>(CHART_EXTRA)!! }

    private val title by lazy { arguments!!.getString(TITLE_EXTRA)!! }

    private val type by lazy { arguments!!.getInt(TYPE_EXTRA)!! }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_title.text = title

        getLineChar()
    }

    private fun getLineChar(){
        val monthNames: Array<String> = arrayOf("Янв", "Фев", "Мар", "Апр", "Май", "Июнь", "Июль", "Авг", "Сент","Окт",
            "Нояб", "Дек")

        val lineEntries = mutableListOf<Entry>()

//        if (chartData.x.size == chartData.y.size)
            chartData.x.forEachIndexed { index, d -> lineEntries.add(Entry(index.toFloat(), d.toFloat())) }

        val lineDataSet = LineDataSet(lineEntries, "")
        val lineData = LineData(lineDataSet)

        when (type) {
            0 -> {
                lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                lineDataSet.setDrawFilled(true)
            }
            1 -> {
                lineDataSet.mode = LineDataSet.Mode.STEPPED
                lineDataSet.fillColor = Color.RED
                lineDataSet.setDrawFilled(true)
                lineDataSet.valueTextSize = 0f
                lineData.setDrawValues(false)
                lineDataSet.setDrawCircles(false)
                lineDataSet.color = context!!.resources.getColor(android.R.color.transparent)
                chart.xAxis.setDrawGridLines(false)
            }
            2 -> {
                lineDataSet.mode = LineDataSet.Mode.LINEAR
                lineDataSet.setDrawFilled(true)
                lineDataSet.fillColor = Color.GREEN
                lineDataSet.color = Color.GREEN
            }

            3 -> {
                lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                lineDataSet.setDrawFilled(false)
                lineDataSet.lineWidth = 2f
                lineDataSet.color = Color.GRAY
                lineDataSet.setDrawCircles(false)
                lineDataSet.valueTextSize = 10f
            }
        }


        chart.apply {
            legend.isEnabled = false
            description.isEnabled = false
            setScaleEnabled(false)

            xAxis.position = XAxis.XAxisPosition.BOTH_SIDED
            data = lineData
//            animateY(1000)

            axisLeft.apply {
//                setDrawGridLines(false)
                setDrawAxisLine(false)
                isEnabled = false
            }

            axisRight.apply {
                setDrawAxisLine(false)
                valueFormatter = IAxisValueFormatter { value, _ -> "${(value.toInt())} тыс" }
                textColor = context!!.resources.getColor(R.color.gray)
//                gridLineWidth = 0.5f
            }

            with(xAxis) {
//                setDrawGridLines(false)
                setDrawAxisLine(false)
                textSize = 12f
                position = XAxis.XAxisPosition.BOTTOM
                xAxis.textColor = context!!.resources.getColor(R.color.gray)
                valueFormatter = IndexAxisValueFormatter(monthNames)
                granularity = 1f
            }

            setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
                override fun onNothingSelected() {
                }

                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    highlightValue(null)
                }
            })
        }

        chart.invalidate()
    }
}