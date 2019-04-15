package newbbb.service;

import newbbb.model.TxPair;

import java.util.List;

public interface ITxPairService {

    List<TxPair> getList();

    int add(TxPair txPair);

}
