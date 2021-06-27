package app;

import java.util.Scanner;

public class MainApp {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args)   {

        //HtmlParser siteParser = new HtmlParser("https://www.simbirsoft.com/");
        //HtmlParser siteParser = new HtmlParser("https://habr.com/ru/post/345660/");

        start("Введите url");


    }

    public static void start(String text) {
        System.out.println(text);
        String url = input.nextLine();

        try {
            HtmlParser siteParser = new HtmlParser(url);
            siteParser.getStatistic();
            siteParser.savePage();
        } catch (IllegalArgumentException  | NullPointerException e) {
            ExceptionUtils.writeExceptionToFile(e);
            start("Ошибка в url-адресе, попробуйте еще раз:");

        } catch (Exception e) {
            ExceptionUtils.writeExceptionToFile(e);
            start("Что то пошло не так... " + "\nПопробуйте еще раз:");
        }


    }


}
