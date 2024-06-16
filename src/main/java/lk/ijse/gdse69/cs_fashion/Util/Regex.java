package lk.ijse.gdse69.cs_fashion.Util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField){
            case ID:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case NAME:
                filed = "(^[A-Za-z]{3,16})([ ]{0,1})([A-Za-z]{2,16})?([ ]{0,1})?([A-Za-z]{3,16})?([ ]{0,1})?([A-Za-z]{3,16})";
                break;
            case ADDRESS:
                filed = "^[A-z|\\\\s]{3,}$";
                break;
            case TEL:
                filed ="^\\d{10}$";
                break;
            case PRICE:
                filed = "^\\d+(\\.\\d{2})?$";
                break;
            case JOB_TITLE:
                filed = "\\b[sS][aA][lL][eE][sS]\\b\n";
                break;
            case QTY:
                filed = "(?<!\\d)(\\d+)(?:\\s+P\\.K\\.T|\\s+KG)(?!\\d)";
                break;
            case PASSWORD:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;

        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }
    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-text-box-border: green; -fx-text-inner-color: green;");

            return true;
        }else {
            textField.setStyle("-fx-text-box-border: red; -fx-text-inner-color: red;");

            return false;
        }
    }

}