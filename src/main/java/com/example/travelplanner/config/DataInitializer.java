package com.example.travelplanner.config;

import com.example.travelplanner.entity.Destination;
import com.example.travelplanner.entity.User;
import com.example.travelplanner.repository.DestinationRepository;
import com.example.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 初始化测试用户
        if (!userRepository.existsByEmail("test@qq.com")) {
            User testUser = new User();
            testUser.setUsername("test");
            testUser.setEmail("test@qq.com");
            testUser.setPassword(passwordEncoder.encode("111111"));
            testUser.setTravelStyle("休闲");
            testUser.setBudgetLevel("中等");
            userRepository.save(testUser);
        }

        // 初始化一些目的地数据
        if (destinationRepository.count() == 0) {
            Destination beijing = new Destination();
            beijing.setName("北京");
            beijing.setDescription("中国的首都，拥有悠久的历史和丰富的文化遗产。天安门广场、故宫、长城等著名景点等你来探索。");
            beijing.setCountry("中国");
            beijing.setCity("北京");
            beijing.setProvince("北京");
            beijing.setImageUrl("https://example.com/beijing.jpg");
            beijing.setLatitude(new BigDecimal("39.9042"));
            beijing.setLongitude(new BigDecimal("116.4074"));
            beijing.setRating(5);
            beijing.setTags(Arrays.asList("历史", "文化", "古迹", "首都"));
            beijing.setRecommendedTravelStyle("文化");
            beijing.setRecommendedBudgetLevel("中等");
            destinationRepository.save(beijing);

            Destination shanghai = new Destination();
            shanghai.setName("上海");
            shanghai.setDescription("中国的经济中心，现代化大都市。外滩、东方明珠、迪士尼乐园等现代与传统完美融合。");
            shanghai.setCountry("中国");
            shanghai.setCity("上海");
            shanghai.setProvince("上海");
            shanghai.setImageUrl("https://example.com/shanghai.jpg");
            shanghai.setLatitude(new BigDecimal("31.2304"));
            shanghai.setLongitude(new BigDecimal("121.4737"));
            shanghai.setRating(5);
            shanghai.setTags(Arrays.asList("现代", "购物", "美食", "国际化"));
            shanghai.setRecommendedTravelStyle("奢华");
            shanghai.setRecommendedBudgetLevel("高端");
            destinationRepository.save(shanghai);

            Destination hangzhou = new Destination();
            hangzhou.setName("杭州");
            hangzhou.setDescription("人间天堂，以西湖美景闻名。古典园林、茶文化、丝绸之路的起点之一。");
            hangzhou.setCountry("中国");
            hangzhou.setCity("杭州");
            hangzhou.setProvince("浙江");
            hangzhou.setImageUrl("https://example.com/hangzhou.jpg");
            hangzhou.setLatitude(new BigDecimal("30.2741"));
            hangzhou.setLongitude(new BigDecimal("120.1551"));
            hangzhou.setRating(4);
            hangzhou.setTags(Arrays.asList("自然", "文化", "茶叶", "西湖"));
            hangzhou.setRecommendedTravelStyle("休闲");
            hangzhou.setRecommendedBudgetLevel("中等");
            destinationRepository.save(hangzhou);

            Destination xian = new Destination();
            xian.setName("西安");
            xian.setDescription("古代丝绸之路的起点，拥有兵马俑、大雁塔等世界级文化遗产。");
            xian.setCountry("中国");
            xian.setCity("西安");
            xian.setProvince("陕西");
            xian.setImageUrl("https://example.com/xian.jpg");
            xian.setLatitude(new BigDecimal("34.3416"));
            xian.setLongitude(new BigDecimal("108.9398"));
            xian.setRating(5);
            xian.setTags(Arrays.asList("历史", "古迹", "兵马俑", "美食"));
            xian.setRecommendedTravelStyle("文化");
            xian.setRecommendedBudgetLevel("经济");
            destinationRepository.save(xian);

            System.out.println("初始化数据完成！");
        }
    }
} 