package com.wuxp.security.example.controller.cms;

import com.wuxp.api.context.InjectField;
import com.wuxp.api.signature.ApiSignature;
import com.wuxp.security.example.request.TestRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/example_cms")
@Tag(name = "example_cms", description = "example_cms")
@Slf4j
public class ExampleCmsController {


    @GetMapping("get_num")
    public List<Integer> getNums(@ApiSignature Integer num, @InjectField(value = "#ip") String ip) {

        log.info("ip {}", ip);
        return Arrays.asList(num, num + 1);
    }

    @GetMapping("get_maps")
    public List<Map<Integer, String>> getMaps(Integer num) {

        Map<Integer, String> map = new HashMap<>();
        map.put(num, "num");
        return Arrays.asList(map);
    }

    @GetMapping("get_map")
    public Map<String, Integer> getMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("num", 1);
        return map;
    }

    @GetMapping("get_map_2")
    public Map<String, List<Boolean>> getMap2() {
        Map<String, List<Boolean>> map = new HashMap<>();
        map.put("num", Arrays.asList(false, true));
        return map;
    }

    @GetMapping("test_inject")
    public String testInject(TestRequest request) {
        log.info("test inject {}", request);
        return "111";
    }
}
