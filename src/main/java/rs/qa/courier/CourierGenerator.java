package rs.qa.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public Courier courierDefault() {
        return new Courier("ninja", "1234", "saske");
    }
    public Courier random() {
        return new Courier(RandomStringUtils.randomAlphanumeric(5), "1234", "saske");
    }
}
