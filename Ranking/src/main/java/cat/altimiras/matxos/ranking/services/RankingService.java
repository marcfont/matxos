package cat.altimiras.matxos.ranking.services;


import cat.altimiras.matxos.ranking.pojo.ReadRanking;

import java.util.List;

public interface RankingService {

    List<ReadRanking> rankingControl (String race, String control);
}
