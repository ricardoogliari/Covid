package com.vogella.android.covid.ui.listcountries

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.vogella.android.covid.R
import com.vogella.android.covid.model.Country
import com.vogella.android.covid.model.Data
import kotlinx.android.synthetic.main.fragment_detail_country.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailCountryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailCountryFragment : Fragment() {

    private var country: Country? = null
    private lateinit var viewModel: DetailCountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //TODO: comentar sobre este ponto chave E SUA RELAÇÃO COM O NAVARGS
            country = it.getSerializable("country") as Country
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_country, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailCountryViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, Observer<List<Data?>?>{ dataset ->
            // update UI
            dataset?.let {
                buildChart(it)
            }
        })
        viewModel.loadDataset(country?.country ?: "brazil");
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buildChart(dataset: List<Data?>){
        // no description text
        // no description text
        chart.getDescription().setEnabled(false)

        // enable touch gestures

        // enable touch gestures
        chart.setTouchEnabled(true)

        chart.setDragDecelerationFrictionCoef(0.9f)

        // enable scaling and dragging

        // enable scaling and dragging
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)
        chart.setHighlightPerDragEnabled(true)

        // set an alternative background color

        // set an alternative background color
        chart.setBackgroundColor(Color.WHITE)
        chart.setViewPortOffsets(0f, 0f, 0f, 0f)

        // get the legend (only possible after setting data)

        // get the legend (only possible after setting data)
        val l: Legend = chart.getLegend()
        l.isEnabled = false

        val xAxis: XAxis = chart.getXAxis()
        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE
        xAxis.textSize = 10f
        xAxis.textColor = Color.WHITE
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(true)
        xAxis.textColor = Color.rgb(255, 192, 56)
        xAxis.setCenterAxisLabels(true)
        xAxis.granularity = 1f // one hour

        xAxis.valueFormatter = object : ValueFormatter() {
            private val mFormat: SimpleDateFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)
            override fun getFormattedValue(value: Float): String {
                val millis: Long = TimeUnit.HOURS.toMillis(value.toLong())
                return mFormat.format(Date(millis))
            }
        }

        val leftAxis: YAxis = chart.getAxisLeft()
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 2000f + dataset.maxBy{ it -> it!!.cases }!!.cases
        leftAxis.yOffset = -9f
        leftAxis.textColor = Color.rgb(255, 192, 56)

        val rightAxis: YAxis = chart.getAxisRight()
        rightAxis.isEnabled = false

        setData(dataset)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData(dataset: List<Data?>) {

        // now in hours

        val values: ArrayList<Entry> = ArrayList()

        dataset.forEach {
            it?.let {
                val cal: Calendar = Calendar.getInstance()
                cal.set(it.date.substring(0, 4).toInt(), it.date.substring(5, 7).toInt() - 1, it.date.substring(8, 10).toInt())
                values.add(
                    Entry(
                        TimeUnit.MILLISECONDS.toHours(cal.timeInMillis).toFloat(),
                        it.cases.toFloat()
                    )
                )
            }
        }



        // create a dataset and give it a type
        val set1 = LineDataSet(values, country?.country ?: "Brazil")
        set1.axisDependency = YAxis.AxisDependency.LEFT
        set1.color = ColorTemplate.getHoloBlue()
        set1.valueTextColor = ColorTemplate.getHoloBlue()
        set1.lineWidth = 1.5f
        set1.setDrawCircles(false)
        set1.setDrawValues(false)
        set1.fillAlpha = 65
        set1.fillColor = ColorTemplate.getHoloBlue()
        set1.highLightColor = Color.rgb(244, 117, 117)
        set1.setDrawCircleHole(false)

        // create a data object with the data sets
        val data = LineData(set1)
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(9f)

        // set data
        chart.data = data
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DetailCountryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
