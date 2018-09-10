package com.chenlong.test.controller;

import com.chenlong.test.entity.QQQ;
import com.chenlong.test.entity.ReceiveEntity;
import com.chenlong.test.service.QQQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/")
public class QQQController {
    private Logger logger = LoggerFactory.getLogger(QQQController.class);

    @Resource
    private QQQService QQQservice;


 @RequestMapping(value = "/QQQ", method = RequestMethod.POST)
    @ResponseBody
    public QQQ  QQQController(@RequestBody @Valid ReceiveEntity M,BindingResult result){
        QQQ re=new QQQ();
        String msg=new String();
        if (result.hasErrors()){

            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                msg +=error.getDefaultMessage()+"\t";
            }
                     re.setCode(400);
                     re.setMsg(msg);
                     re.setData(QQQservice.getData(M));
        }else {
            try {
                                 re.setCode(200);
                                 re.setMsg("successfull");
                                  re.setData(QQQservice.getData(M));

            }catch (Exception e){
                                               re.setCode(500);
                                             re.setMsg("服务器内部错误");
                                             re.setData(QQQservice.getData(M));

            }

        }
          return re;
    }



}
