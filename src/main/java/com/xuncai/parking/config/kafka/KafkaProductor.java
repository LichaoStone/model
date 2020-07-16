package com.xuncai.parking.config.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author
 * @since 1.0.0
 */
@Component
@ConditionalOnBean(KafkaProducerConfig.class)
public class KafkaProductor {

    protected static Logger worklog = LoggerFactory.getLogger(KafkaProductor.class);

    @Autowired
    private KafkaProducer<String,String> producer;

    public void sendMessage(String topic, String key, String message) {
        if (key == null) {
            key = System.currentTimeMillis() + "";
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, System.currentTimeMillis(), key,
                message);
        producer.send(record, new Callback() {

            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                // TODO Auto-generated method stub
                if (null != exception) {
                    worklog.info("往主题:{}发送消息:{},出现异常:", topic, message, exception);
                }
                if(null!=metadata) {
                    worklog.info("消息:{},成功投送到主题:{}下的分区:{},该分区当前偏移量:{},该消息时间戳:{}",message,metadata.topic(),metadata.partition(),metadata.offset(),metadata.timestamp());
                }
            }
        });
    }
}
