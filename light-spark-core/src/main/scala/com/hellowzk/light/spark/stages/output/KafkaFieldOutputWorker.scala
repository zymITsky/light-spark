package com.hellowzk.light.spark.stages.output

import com.hellowzk.light.spark.beans.BaseConfig
import com.hellowzk.light.spark.beans.output.KafkaFieldOutputConfig
import org.apache.spark.sql.SparkSession

/**
 * <p>
 * 日期： 2020/4/15
 * <p>
 * 时间： 14:13
 * <p>
 * 星期： 星期三
 * <p>
 * 描述：
 * <p>
 * 作者： zhaokui
 *
 **/
class KafkaFieldOutputWorker extends KafkaOutputWorker {
  override def process(config: BaseConfig)(implicit ss: SparkSession): Unit = {
    val item = config.asInstanceOf[KafkaFieldOutputConfig]
    ss.table(item.srcName).rdd.map(a => a.toSeq.mkString(item.fs)).foreachPartition { case iter =>
      sendData(iter, item.brokers, item.topic)
    }
  }
}
