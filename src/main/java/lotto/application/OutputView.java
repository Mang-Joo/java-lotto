package lotto.application;

import java.util.ArrayDeque;
import lotto.domain.WinningLotto;
import lotto.domain.LottoNumber;
import lotto.domain.LottoPrice;
import lotto.domain.Lottos;
import lotto.domain.Rank;

public class OutputView {

    private final LottoPrice lottoPrice;

    public OutputView(LottoPrice lottoPrice) {
        this.lottoPrice = lottoPrice;
    }

    public void lottoCount(int purchaseMoney) {
        System.out.println(lottoPrice.lottoCount(purchaseMoney) + "개를 구매했습니다.");
    }

    public void lottos(Lottos lottos, int manualCount, int autoCount) {
        System.out.printf("수동으로 %d장, 자동으로 %d장을 구매했습니다.%n", manualCount, autoCount);
        StringBuilder stringBuilder = new StringBuilder();
        lottos.lottos().forEach(lotto -> {
            stringBuilder.append("[");
            ArrayDeque<LottoNumber> lottosList = new ArrayDeque<>(lotto.lotto());
            printLottoNumbers(stringBuilder, lottosList);
        });
        System.out.println(stringBuilder);
    }

    public void winningStatistics(WinningLotto winningLotto) {
        System.out.println("당첨 통계");
        System.out.println("----------");
        StringBuilder stringBuilder = new StringBuilder();
        winningLotto.winningConfirmation()
            .forEach((rank, count) -> stringBuilder.append(getRank(rank, count)));
        System.out.println(stringBuilder);
    }

    private String getRank(Rank rank, Integer count) {
        if (rank == Rank.LOSER) {
            return String.format("낙첨 %d개%n", count);
        }
        if (rank == Rank.SECOND) {
            return String.format("%d개 일치, 보너스볼 포함 (%d원)- %d개%n", rank.matchCount(), rank.reward(), count);
        }
        return String.format("%d개 일치 (%d원)- %d개%n", rank.matchCount(), rank.reward(), count);
    }

    @SuppressWarnings("all")
    private void printLottoNumbers(StringBuilder stringBuilder, ArrayDeque<LottoNumber> lottosList) {
        while (lottosList.size() != 1) {
            LottoNumber lottoNumber = lottosList.poll();
            stringBuilder.append(lottoNumber.value()).append(", ");
        }
        LottoNumber lottoNumber = lottosList.poll();
        stringBuilder.append(lottoNumber.value()).append("]").append("\n");
    }

    public void winningStatistics(WinningLotto winningLotto, int purchase) {
        System.out.println("총 수익률은 " + winningLotto.yield(purchase) + "입니다.");
    }

}
