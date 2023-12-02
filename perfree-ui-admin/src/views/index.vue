<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :span="12"><e-charts class="chart" :option="optionA" /> </el-col>
      <el-col :span="12">
        <div class="grid-content ep-bg-purple" />
        <h3 style="margin: 0; font-size: 18px;font-weight: bold;color: #464646;margin-top: 5px;">成交情况数据</h3>
        <div class="order-panel">
          <p class="item-p" v-for="item in orders">
            <span class="item-p-left">
              <span class="item-p-name">{{ item.name }}</span>
              <span class="item-p-phone">{{ item.phone }}</span>
            </span>
            <span class="item-p-right">{{ item.price }}</span>
          </p>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12"><e-charts class="chart" :option="optionC" /> </el-col>
      <el-col :span="12"><e-charts class="chart" :option="optionD" /></el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const echartDataA = ref([
  { value: 60, name: '渠道人数' },
  { value: 40, name: '预约人数' },
  { value: 20, name: '上门看车人数' },
  { value: 80, name: '最终销售数量' }
])

const echartDataC = ref([70, 34, 60, 78, 69])
const echartDataD = ref([150, 230, 224, 218, 135, 147, 260, 150, 230, 224, 218, 135])

const orderList = ref([
  { "name": "钱七", "phone": "135****3500", "price": 289666 },
  { "name": "施二十一", "phone": "132****1399", "price": 309668 },
  { "name": "楚雄", "phone": "158****2070", "price": 256284 },
  { "name": "何十九", "phone": "155****0793", "price": 490635 },
  { "name": "王二", "phone": "135****1238", "price": 478736 },
  { "name": "钱七", "phone": "137****0446", "price": 282382 },
  { "name": "李四", "phone": "136****0695", "price": 453116 },
  { "name": "冯十一", "phone": "135****3606", "price": 239692 },
  { "name": "张三丰", "phone": "151****2104", "price": 282589 },
  { "name": "温宁", "phone": "132****7868", "price": 277941 }
])

const names = ref(["王二", "张三", "李四", "赵五", "钱六", "孙七", "周八", "吴九",
  "郑十", "冯十一", "陈十二", "杨十三", "黄十四", "朱十五", "秦十六", "尤十七", "许十八",
  "何十九", "吕二十", "施二十一", "张三丰", "张翠山", "张无忌", "张三", "李四", "王五", "赵六",
  "钱七", "孙八", "周九", "吴十", "郑一", "冯二", "陈三", "楚雄", "蓝忘机", "江澄", "温宁", "金凌", "薛洋", "蒋琬"]);
const prefixArray = ref(['130', '131', '132', '133', '135', '136', '137', '138', '139', '147', '150', '151', '152', '153', '155', '156', '157', '158', '159', '186', '187', '188', '189']);
const timer = setInterval(() => {
  echartDataA.value = echartDataA.value.map(item => ({
    ...item,
    value: Math.floor(Math.random() * 100) + 1,

  }))

  echartDataC.value = [];

  for (let i = 0; i < 6; i++) {
    echartDataC.value.push(Math.floor(Math.random() * 100));
  }

  echartDataD.value = [];

  for (let i = 0; i < 13; i++) {
    echartDataD.value.push(Math.floor(Math.random() * 100));
  }

  orderList.value = orderList.value.map(item => ({
    ...item,
    price: Math.floor(Math.random() * (500000 - 50000 + 1)) + 50000,
    name: names.value[Math.floor(Math.random() * names.value.length)],
    phone: prefixArray.value[Math.floor(Math.random() * prefixArray.value.length)] + '****' + Math.random().toString().slice(7, 11)
  }))
}, 60000);

onBeforeUnmount(() => {
  clearInterval(timer); //清除定时器
})

const optionA = computed(() => {
  return {
    title: {
      text: '当天销售车辆转化率'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c}%'
    },
    legend: {
      data: ['渠道人数', '预约人数', '上门看车人数', '最终销售数量']
    },
    series: [
      {
        name: '当天销售车辆转化率',
        type: 'funnel',
        left: '10%',
        top: 60,
        bottom: 60,
        width: '80%',
        min: 0,
        max: 100,
        minSize: '30%',
        maxSize: '100%',
        sort: 'descending',
        gap: 2,
        label: {
          position: 'inside',
          formatter: '{b}: {c}   {d}%',
          color: '#fff'
        },
        itemStyle: {
          borderColor: '#fff',
          borderWidth: 1
        },
        emphasis: {
          label: {
            fontSize: 20
          }
        },
        data: echartDataA.value.map(el => el)
      }
    ]
  };
})

const optionC = computed(() => {
  return {
    title: {
      text: '渠道统计'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    // 指定图形与Dom距离
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      // y轴刻度上文字与Dom边界保留距离
      containLabel: true
    },
    xAxis: {
      // 设置x轴不显示
      show: false
    },
    // 需要使用两个y轴，使用列表存储
    yAxis: [{
      type: 'category',
      data: ['抖音', '小红书', '朋友圈', '微信', '客户介绍'],
      // 将数据显示方式倒转
      inverse: true,
      // 不显示y轴刻度线
      axisLine: {
        show: false
      },
      // 不显示y轴刻度值
      axisTick: {
        show: false
      },
      // 设置第一个y轴上的文字颜色
      axisLabel: {
        // color:"blue"
      }
    },
    {
      // 先设置第二个y轴显示
      show: true,
      data: echartDataC.value.map(el => el),
      // 将数据显示方式倒转
      inverse: true,
      // 不显示y轴刻度线
      axisLine: {
        show: false
      },
      // 不显示y轴刻度值
      axisTick: {
        show: false
      },
      // 设置第二个y轴上文字的颜色与字号
      axisLabel: {
        textStyle: {
          fontSize: 12,
          //color:"blue"
        }
      }
    }
    ],
    series: [
      {
        showBackground: true,
        name: '渠道统计',
        type: 'bar',
        data: echartDataC.value.map(el => el),
        // 设置柱子宽度
        barWidth: 10,
        // 设置第一个y轴显示的深浅度，第二个显示在第一个之上
        yAxisIndex: 0,
        // 注意之间的距离
        barCategoryGap: 10,
        // 设置柱子的位置，柱子上显示的内容
        label: {
          show: false,
          position: "inside",
          // {c} 表示数值，{a}表示serie名,{b}表示系列名
          formatter: "{c}%"
        },
        itemStyle: {
          // 设置柱子颜色
          color: function (params) {
            // 指定每个柱子的颜色
            var colorList = ['#409EFF', '#F72C5B', '#409EFF', '#F72C5B', '#409EFF'];
            return colorList[params.dataIndex];
          },
          // 设置柱子为圆角
          barBorderRadius: 10
        }
      }
    ]
  }
})

const optionD = computed(() => {
  return {
    title: {
      text: '月销售量'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        symbolSize: 15,
        data: echartDataD.value.map(el => el),
        type: 'line'
      }
    ]
  }
})

const orders = computed(() => {
  return orderList.value.map(el => el)
})

</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
}

.chart {
  height: 400px;
}

.item-p {
  margin-top: 15px;
  margin-bottom: 0;
  display: flex;
  align-items: center;
}

.item-p-left {
  flex: 1 1 50%;
}

.item-p-right {
  flex: 1 1 50%;
  color: #f72c5b;
}

.order-panel {
  height: 385px;
  overflow: auto;
}

.order-panel::-webkit-scrollbar {
  display: none;
}

.item-p-name {
  width: 100px;
  display: inline-block;
}</style>

