package newbbb.matchengine;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@org.springframework.core.annotation.Order(11)
public class MEHistory {



    @PostConstruct
    private void InitHistory(){

    }

}
