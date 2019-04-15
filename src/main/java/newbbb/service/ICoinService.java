package newbbb.service;

import newbbb.model.Coin;

import java.util.List;

public interface ICoinService {

    List<Coin> getList();

    int add(Coin coin);

}
