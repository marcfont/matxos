package cat.matxos.ranking.services;


import cat.matxos.ranking.pojo.FilterRanking;
import cat.matxos.ranking.pojo.ReadRanking;

import java.util.List;

public interface RankingService {

    List<ReadRanking> rankingControl (String race, String control, FilterRanking filterRanking);

    List<ReadRanking> rankingBib(String race, String bib);

    List<ReadRanking> ranking(String race, FilterRanking filterRanking);
}
