package ir.dotin.model.repository;

import ir.dotin.exception.DepositTypeMismatchException;
import ir.dotin.exception.InvalidDepositBalanceException;
import ir.dotin.exception.InvalidDurationInDaysException;
import ir.dotin.model.DepositFactory;
import ir.dotin.model.data.Deposit;
import ir.dotin.model.data.DepositType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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
    private static final List<Deposit> deposits = new ArrayList<>();

    /**
     * this method initialize and create a document for parse xml file.
     * @return Document this return created document.
     * */
    private static Document getDocument() {
        Document document = null;
        try {
            File depositsInfoFile = new File("src/resource/deposits-info");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(depositsInfoFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * this method fetch all valid deposits using document from xml file.
     * @return List<Deposit> this returns all valid deposits that fetched from xml file.
     * */
    public static List<Deposit> getAllDeposits() {
        Document document = getDocument();
        NodeList depositNodes = document.getElementsByTagName("deposit");
        for (int i = 0; i < depositNodes.getLength(); i++) {
            boolean hasError;
            Node depositNode = depositNodes.item(i);
            if (depositNode.getNodeType() == Node.ELEMENT_NODE) {
                Element depositElement = (Element) depositNode;
                String customerNumber = depositElement.getElementsByTagName("customerNumber").item(0).getTextContent();
                DepositType depositType = DepositType.getDepositType(depositElement.getElementsByTagName("depositType").item(0).getTextContent());
                hasError = checkValidateDepositType(depositType, i);
                BigDecimal depositBalance = new BigDecimal(depositElement.getElementsByTagName("depositBalance").item(0).getTextContent());
                hasError = checkValidateDepositBalance(depositBalance, i) || hasError;
                int durationInDays = Integer.parseInt(depositElement.getElementsByTagName("durationInDays").item(0).getTextContent());
                hasError = checkValidateDurationInDays(durationInDays, i) || hasError;
                if (!hasError) {
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
            }
        }
        return deposits;
    }

    private static boolean checkValidateDepositType(DepositType depositType, int depositIndex) {
        if (depositType == null) {
            try {
                throw new DepositTypeMismatchException("Error[Deposit" + (depositIndex + 1) + "]: The deposit type is not valid!");
            } catch (DepositTypeMismatchException e) {
                System.out.println(e.getMessage());
                return true;
            }
        }
        return false;
    }

    private static boolean checkValidateDepositBalance(BigDecimal depositBalance, int depositIndex) {
        if (depositBalance.compareTo(BigDecimal.ZERO) <= 0) {
            try {
                throw new InvalidDepositBalanceException("Error[Deposit" + (depositIndex + 1) + "]: The deposit balance is less than zero!");
            } catch (InvalidDepositBalanceException e) {
                System.out.println(e.getMessage());
                return true;
            }
        }
        return false;
    }

    private static boolean checkValidateDurationInDays(int durationInDays, int depositIndex) {
        if (durationInDays <= 0) {
            try {
                throw new InvalidDurationInDaysException("Error[Deposit" + (depositIndex + 1) + "]: The duration in days is equal or less than zero!");
            } catch (InvalidDurationInDaysException e) {
                System.out.println(e.getMessage());
                return true;
            }
        }
        return false;
    }

    /**
     * this method writes customer number & payed interest per deposit in the txt file.
     * @return void nothing.
     * */
    public static void writeDepositsPayedInterestInfoInFile() {
        try {
            FileWriter writer = new FileWriter("src/resource/payed-interests-info.txt");
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
