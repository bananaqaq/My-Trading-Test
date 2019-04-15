package newbbb.trading;

import newbbb.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tradingTest")
public class TradingTest {

    @Autowired
    private IAccountService accountService;

    public void makeTrade(){
        accountService.sayHello();

    }

}
