import java.util.HashMap;
import java.util.Map;

public class RiskManagementService {
    private Map<String, Double> exposureLimits = new HashMap<>();

    public void setExposureLimit(String symbol, double limit) {
        exposureLimits.put(symbol, limit);
    }

    public boolean checkRisk(String symbol, double position) {
        Double limit = exposureLimits.get(symbol);
        return limit != null && position <= limit;
    }

    public static void main(String[] args) {
        RiskManagementService riskService = new RiskManagementService();
        riskService.setExposureLimit("AAPL", 10000);

        boolean withinRisk = riskService.checkRisk("AAPL", 5000);
        System.out.println("Within risk limits: " + withinRisk);
    }
}
