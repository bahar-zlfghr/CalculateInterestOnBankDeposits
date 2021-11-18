package ir.dotin.model.repository;

import ir.dotin.model.DepositFactory;
import ir.dotin.model.data.Deposit;
import ir.dotin.model.data.DepositType;
import ir.dotin.util.DepositUtil;
import ir.dotin.util.DocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Bahar Zolfaghari
 **/
public abstract class DepositRepository {
    private static final String ROOT = "src/resource";
    private static final List<Deposit> deposits = new ArrayList<>();
    private static final List<Exception> exceptions = new ArrayList<>();

    /**
     * @return List<Exception> return all exceptions that occurred.
     * */
    public static List<Exception> getExceptions() {
        return exceptions;
    }

    /**
     * this method fetch all valid deposits using document from xml file.
     * @return List<Deposit> this returns all valid deposits that fetched from xml file.
     */
    public static List<Deposit> getAllDeposits() {
        Document document = DocumentUtil.getDocument(ROOT + "/deposits-info");
        NodeList depositNodes = document.getElementsByTagName("deposit");
        for (int i = 0; i < depositNodes.getLength(); i++) {
            exceptions.clear();
            Node depositNode = depositNodes.item(i);
            if (depositNode.getNodeType() == Node.ELEMENT_NODE) {
                Element depositElement = (Element) depositNode;
                String customerNumber = depositElement.getElementsByTagName("customerNumber").item(0).getTextContent();
                DepositType depositType = DepositType.getDepositType(depositElement.getElementsByTagName("depositType").item(0).getTextContent());
                DepositUtil.checkValidateDepositType(depositType, i);
                BigDecimal depositBalance = new BigDecimal(depositElement.getElementsByTagName("depositBalance").item(0).getTextContent());
                DepositUtil.checkValidateDepositBalance(depositBalance, i);
                int durationInDays = Integer.parseInt(depositElement.getElementsByTagName("durationInDays").item(0).getTextContent());
                DepositUtil.checkValidateDurationInDays(durationInDays, i);
                if (exceptions.isEmpty()) {
                    try {
                        Method createDeposit = DepositFactory.class.getDeclaredMethod("createDeposit", DepositType.class);
                        Object object = createDeposit.invoke(null, depositType);
                        if (object instanceof Deposit) {
                            Deposit deposit = (Deposit) object;
                            Class<? extends Deposit> depositClass = deposit.getClass();
                            Method setCustomerNumber = depositClass.getMethod("setCustomerNumber", String.class);
                            setCustomerNumber.invoke(deposit, customerNumber);
                            Method setDepositBalance = depositClass.getMethod("setDepositBalance", BigDecimal.class);
                            setDepositBalance.invoke(deposit, depositBalance);
                            Method setDurationInDays = depositClass.getMethod("setDurationInDays", int.class);
                            setDurationInDays.invoke(deposit, durationInDays);
                            Method setPayedInterest = depositClass.getMethod("setPayedInterest", BigDecimal.class);
                            Method calculateInterest = depositClass.getMethod("calculateInterest");
                            Object result = calculateInterest.invoke(deposit);
                            setPayedInterest.invoke(deposit, new BigDecimal(result.toString()));
                            deposits.add(deposit);
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    exceptions.forEach(e -> System.out.println(e.getMessage()));
                }
            }
        }
        return deposits;
    }

    /**
     * this method writes customer number & payed interest per deposit in the txt file.
     * @return void nothing.
     */
    public static void writeDepositsPayedInterestInfoInFile() {
        try {
            FileWriter writer = new FileWriter(ROOT + "/payed-interests-info.txt");
            writer.write("customerNumber#payedInterest\n");
            deposits.forEach(deposit -> {
                try {
                    writer.write(deposit.getCustomerNumber() + "#" + deposit.getPayedInterest() + "\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
