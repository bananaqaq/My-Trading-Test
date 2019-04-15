package newbbb.service.impl;

import newbbb.service.IAccountService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

    @Override
    public void sayHello() {
        System.out.println("hello");
    }

}
