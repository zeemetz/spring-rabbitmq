package com.mycimb.example.rabbitmq.controller;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/v1")
@RestController
public class QueueController {

    @Value("${rabbitmq.exchange.name}")
    private String EXCHANGE;
    @Value("${rabbitmq.routing.key}")
    private String ROUTING_KEY;
    @Value("${rabbitmq.queue.name}")
    private String QUEUE_NAME;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/publish")
    @ResponseBody
    public String[] publishQueue(@RequestBody PublishRequest request){
        for (String message:
             request.getMessage()) {
            rabbitTemplate.convertAndSend(this.EXCHANGE,this.ROUTING_KEY,message);
        }
        return request.getMessage();
    }

    @GetMapping(value = "/consume")
    @ResponseBody
    public List<String> consumeQueue(@RequestBody ConsumeRequest request){

        List<String> res = new ArrayList<>();

        for (int i=0; i<request.getCount(); i++){
            Object message = rabbitTemplate.receiveAndConvert(this.QUEUE_NAME);
            if(message==null){
                break;
            }
            res.add(message.toString());
        }
        return res;
    }
}
