<template>
  <LineGen
    :chart-options="chartOptions"
    :chart-data="chartData"
    :chart-id="chartId"
    :dataset-id-key="datasetIdKey"
    :plugins="plugins"
    :css-classes="cssClasses"
    :styles="styles"
    :width="width"
    :height="height"
  />
</template>

<script>
import { Line as LineGen } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
} from "chart.js";
import { mapState } from "vuex";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement
);

export default {
  name: "LineChart",
  components: { LineGen },
  props: {
    chartId: {
      type: String,
      default: "bar-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Object,
      default: () => {},
    },
  },
  computed: {
    ...mapState("dealViewStore", ["houseDealList"]),
  },
  watch: {
    houseDealList: function () {
      console.log("houseDealList changed");

      this.chartData.labels = [];
      this.chartData.datasets[0].data = [];

      this.houseDealList.forEach((element) => {
        // let thisDate = element.dealYear + element.dealMonth + element.dealDay;
        let gDate = `${element.dealYear}-${
          element.dealMonth >= 10 ? element.dealMonth : "0" + element.dealMonth
        }-${element.dealDay >= 10 ? element.dealDay : "0" + element.dealDay}`;
        let thisDealAmount = element.dealAmount.replace(",", "") + "000";
        this.chartData.labels.push(gDate);
        this.chartData.datasets[0].data.push(thisDealAmount);
      });
      // console.log("data" + this.chartData.datasets[0].data);
    },
  },
  data() {
    return {
      chartData: {
        labels: [],
        // datasets: [{ data: [] }],
        datasets: [
          {
            data: [],
            label: "실거래가(원)",
            borderColor: "#e04f4f",
          },
        ],
      },
      chartOptions: {
        responsive: true,
      },
    };
  },
  methods: {},
};
</script>
