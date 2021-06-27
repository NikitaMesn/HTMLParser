package app;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HtmlParser {
    private final String pageURL;
    private Document page;


    public HtmlParser(String pageURL)  {
        this.pageURL = pageURL;
        connect();

    }


    private String getFileName() {
        return  page
                .title()
                .replaceAll("[^A-Za-zА-Яа-я]+", "_").trim() + ".html";
    }


    private void connect() {
        try {
            this.page = Jsoup.connect(pageURL).get();
        } catch (IOException e) {
            ExceptionUtils.writeExceptionToFile(e);
            if (page == null) throw new NullPointerException(pageURL + "doesn't exist"); // отправим ошибку - нет такой страницы
        }
    }

    public void getStatistic() {
        String text = page.text();
        String fileName = getFileName();

         //Создаем map для подсчета каждого слова
        Map<String, Long> mapWithCountWord = Stream.of(text.split("[^A-Za-zА-Яа-я]+"))
                //.filter(x -> x.length() > 2) // можно убрать союзы, но в задании такого не было
                .map(String::toUpperCase) //приводим к верхнему регистру
                .collect(Collectors.groupingBy( Function.identity(),Collectors.counting()));

        //выводим в консоль
        mapWithCountWord.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) //сортируем по убыванию
                .forEach(e -> System.out.println(e.getKey() +" - " + e.getValue())); //вывод как в примере

        JsonUtils.saveToJsonDB(fileName, mapWithCountWord); //сохраняем статистику в базу данных в виде JSON
    }


    /*
    Публичный метод для сохранения страницы в файл
     */
    public void savePage() {
        String fileName = getFileName();
        String outerHtml = page.outerHtml();

        try(FileWriter writer = new FileWriter(fileName, false))
        {
            writer.write(outerHtml);
            writer.flush();
        }
        catch(IOException ex){
            ExceptionUtils.writeExceptionToFile(ex);;
        }

    }
}