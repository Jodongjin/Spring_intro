package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // request uri
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello"; // resources/templates 에서 찾음
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
    /*
        - command + p: show options
        - @RequestParam의 옵션 중 required -> default = true
        - 즉, request 시, 해당 parameter를 필수로 전달해야 함
        - ex) localhost:8080/hello-mvc?name=dongjin
     */

    /* String return */
    @GetMapping("hello-string")
    @ResponseBody // HTTP body에 직접 데이터를 담아서 response
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    /* JSON return */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello { // HelloController.Hello로 접근 가능
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

/*
    Tip
    - cmd + shift + enter: 자동 완성
 */

/*
    Note
    - xml: json 이전에 많이 사용했던 data form, 무겁고 태그를 열고 닫아야 하는 단
    - JsonConverter: JSON(Jackson)  vs  GSON(google)
 */