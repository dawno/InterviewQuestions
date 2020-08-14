import java.io.*;
import java.text.DateFormatSymbols;
import java.util.*;
public class Mobiux {
    String address= "/Users/utkarshsingh/Documents/MobiuxData.txt";//Address of the file which contains the data
     //The class which contains the sales data
     class Data{
     String day;
     String month;
     String year;
     String sku;
     int price;
     int quantity;
     int totalPrice;

      Data(String day, String month, String year, String sku, int price, int quantity, int totalPrice) {
       this.day = day;
       this.month = month;
       this.year = year;
       this.sku = sku;
       this.price = price;
       this.quantity = quantity;
       this.totalPrice = totalPrice;
     }

      String getDay() {
       return day;
     }

      void setDay(String day) {
       this.day = day;
     }

      String getMonth() {
       return month;
     }

      void setMonth(String month) {
       this.month = month;
     }

      String getYear() {
       return year;
     }

     void setYear(String year) {
       this.year = year;
     }

      String getSku() {
       return sku;
     }

      void setSku(String sku) {
       this.sku = sku;
     }

      int getPrice() {
       return price;
     }

      void setPrice(int price) {
       this.price = price;
     }

      int getQuantity() {
       return quantity;
     }

      void setQuantity(int quantity) {
       this.quantity = quantity;
     }

      int getTotalPrice() {
       return totalPrice;
     }

      void setTotalPrice(int totalPrice) {
       this.totalPrice = totalPrice;
     }

   }




    public static void main(String[] args){
     List<String> salesDataList= new ArrayList<>();
     List<Data>dataList= new ArrayList<>();
     Mobiux mobiux= new Mobiux();
     try{
     salesDataList=  mobiux.readData();// func to read the file
     dataList= mobiux.processData(salesDataList);//func to process the data and seggregate it into Sales data category
     System.out.println("\n ***The computation of question 1 ***");
     mobiux.processTotalSalesOfStore(dataList);//func to process 1st requirement
     System.out.println("\n ***The computation of question 2***");
     mobiux.processMonthsWiseSalesTotal(dataList);// func to process 2nd requirement
     System.out.println("\n ***The computation of question 3***");
     mobiux.processMostPopularItemMonthWise(dataList);// func to process 3rd requirement
     System.out.println("\n ***The computation of question 4***");
     mobiux.processMostRevenueGeneratingItem(dataList);// func to process 4th requirement
     System.out.println("\n ***The computation of question 5***");
     mobiux.processPopularItemForMinMaxAndAverageItem(dataList);// func to process 5th requirement
     }
     catch(Exception exception){
        System.out.println("The error message is : "+exception.getMessage());//catching the exception
     }
     
    }
    public void processPopularItemForMinMaxAndAverageItem(List<Data>dataList){
      DateFormatSymbols dfs = new DateFormatSymbols();
      String[]months= dfs.getMonths();// array to store the months
      String mostPopularItem="";
      int maxQuantity=0;
      int m;
      int average ;
      HashMap<String,Integer>popularityList= new HashMap<>();// map to store sku name and quantity
      for(Data data: dataList){
        if(popularityList.containsKey(data.getSku())){
           popularityList.put(data.getSku(), popularityList.get(data.sku)+data.getQuantity());
        }
        else{
           popularityList.put(data.getSku(),data.getQuantity());
        }
      }
      for(String skuName : popularityList.keySet()){ // calculating the maximum sku which has the maximum quantity
         if(maxQuantity<popularityList.get(skuName)){
           maxQuantity= popularityList.get(skuName);
           mostPopularItem= skuName;
         }
      }
     
      HashMap<String,HashMap<String,Integer>> monthwiseCalculation = new HashMap<>();
      HashMap<String,Integer> emptyTempMap;
      for(Data data : dataList){
         emptyTempMap = new HashMap<>(); // temp map to store the min,max and average of the most popular item
        emptyTempMap.put("min", Integer.MAX_VALUE);
        emptyTempMap.put("max", Integer.MIN_VALUE);
        emptyTempMap.put("noOfOrders", 0);
        emptyTempMap.put("totalOrders", 0);
        if(monthwiseCalculation.containsKey(data.getMonth())){
           if(data.getSku().equalsIgnoreCase(mostPopularItem)){
              HashMap<String,Integer> temp = monthwiseCalculation.get(data.getMonth()); // map to store month and the quantity respectively
              if(temp.get("min")>data.getQuantity()){
                temp.put("min", data.getQuantity());
              }
              if(temp.get("max")<data.getQuantity()){
                temp.put("max", data.getQuantity());
              }
             temp.put("noOfOrders", temp.get("noOfOrders")+1);
             temp.put("totalOrders",temp.get("totalOrders")+data.getQuantity());
           }
           
        }
        else{
             monthwiseCalculation.put(data.getMonth(), emptyTempMap);
        }
      }
      System.out.println("The min , max and average of the most popular item for each month is - : ");
      for(String month: monthwiseCalculation.keySet()){
        emptyTempMap= new HashMap<>();
        emptyTempMap = monthwiseCalculation.get(month);
        m= Integer.parseInt(month);
        average = emptyTempMap.get("totalOrders")/emptyTempMap.get("noOfOrders");
        System.out.println("The most popular item in month of "+months[m-1] +" is "+ mostPopularItem+ " minimum order = "+ emptyTempMap.get("min")+" maximum order = "+emptyTempMap.get("max")+" average quantity per order = "+average);
      }
    }
    public void processMostRevenueGeneratingItem(List<Data>dataList){
      DateFormatSymbols dfs = new DateFormatSymbols();
      String[]months= dfs.getMonths();
      String mostRevenueSku="";
      String sku;int totalPrice;
      int m;
      int maxRevenue=0;
      HashMap<String,Integer> skuRevenue= new HashMap<>();// map to store sku and revenue generated respectively
      HashMap<String , HashMap<String,Integer>> popularityList= new HashMap<>();//map to store the revenue of the sku for each month(key)
      for(Data data : dataList){
        if(popularityList.containsKey(data.getMonth())){
           sku=data.getSku();
           totalPrice=data.getTotalPrice();
           skuRevenue= popularityList.get(data.getMonth());
           if(skuRevenue.containsKey(sku)){
            skuRevenue.put(sku, skuRevenue.get(sku)+totalPrice);
               popularityList.put(data.getMonth(),skuRevenue );
           }
           else{
            skuRevenue.put(sku, totalPrice);
            popularityList.put(data.getMonth(),skuRevenue );
           }
        }
        else{
          skuRevenue= new HashMap<>();
            popularityList.put(data.getMonth(), skuRevenue);
        }
      }
      System.out.println("The most revenue generating item of each months are -: ");
      for(String month: popularityList.keySet()){

        skuRevenue= popularityList.get(month);

        for(String skuName:skuRevenue.keySet()){
            if(maxRevenue<skuRevenue.get(skuName)){
              maxRevenue = skuRevenue.get(skuName);
               mostRevenueSku=skuName;
            }
        }
        m= Integer.parseInt(month);
          System.out.println("The most revenue generating item of month " +months[m-1]+" is "+ mostRevenueSku);

          maxRevenue=0;
      }
    }
    public void processMostPopularItemMonthWise(List<Data>dataList){
      DateFormatSymbols dfs = new DateFormatSymbols();
      String[]months= dfs.getMonths(); // array to store the months
      String mostPopularSku="";
      String sku;int quantity;
      int m;
      int maxQuantity=0;
      HashMap<String,Integer> skuQuantity= new HashMap<>();// map to store sku and quantity respectively
      HashMap<String , HashMap<String,Integer>> popularityList= new HashMap<>();// map to store sku and quantity of each month(key)
      for(Data data : dataList){
        if(popularityList.containsKey(data.getMonth())){
           sku=data.getSku();
           quantity=data.getQuantity();
           skuQuantity= popularityList.get(data.getMonth());
           if(skuQuantity.containsKey(sku)){
               skuQuantity.put(sku, skuQuantity.get(sku)+quantity);
               popularityList.put(data.getMonth(),skuQuantity );
           }
           else{
            skuQuantity.put(sku, quantity);
            popularityList.put(data.getMonth(),skuQuantity );
           }
        }
        else{
            skuQuantity= new HashMap<>();
            popularityList.put(data.getMonth(), skuQuantity);
        }
      }
      System.out.println("The most popular item of each months are -: ");
      for(String month: popularityList.keySet()){

        skuQuantity= popularityList.get(month);

        for(String skuName:skuQuantity.keySet()){
            if(maxQuantity<skuQuantity.get(skuName)){
               maxQuantity = skuQuantity.get(skuName);
               mostPopularSku=skuName;
            }
        }
        m= Integer.parseInt(month);
          System.out.println("The most popular item of month " +months[m-1]+" is "+ mostPopularSku);

          maxQuantity=0;
      }

    }
    public void processMonthsWiseSalesTotal(List<Data>dataList)throws NumberFormatException{
      String month;
      int totalPrice;
      DateFormatSymbols dfs = new DateFormatSymbols();
      String[]months= dfs.getMonths();
      HashMap<String,Integer> monthsSalesMap= new HashMap<>(); // map to store the month and the total price
       for(Data data: dataList){
         month= data.getMonth();
         totalPrice= data.getTotalPrice();
         if(monthsSalesMap.containsKey(month)){
             monthsSalesMap.put(month, monthsSalesMap.get(month)+totalPrice);
         }
         else{
                 monthsSalesMap.put(month, totalPrice);
         }
       }
       System.out.println("The Sales Month wise are -: ");
       for(String m: monthsSalesMap.keySet()){
         int monthInt=  Integer.parseInt(m);
         System.out.println("The sales in month "+months[monthInt-1]+" is "+ monthsSalesMap.get(m));
       }
    }

    public void processTotalSalesOfStore(List<Data>dataList){// func to get the total sales
      int totalSalesSum=0;
      for(Data data:dataList){
      totalSalesSum+=  data.getTotalPrice();
      }
      System.out.println("#1 Total sales of the store "+totalSalesSum);
    }


    public  List<Data> processData(List<String> salesDataList){
      List<Data>dataList= new ArrayList<>();
      String day, month,year,sku;
      int price ,quantity,totalPrice;
            String[] temp= salesDataList.get(0).split(",");
            HashMap<String,Integer> tempMap= new HashMap<>();
            for(int i=0;i<temp.length;i++){
                tempMap.put(temp[i],i);
            }
          salesDataList.remove(0);
      for(String s:salesDataList){  // processing the raw data to seggregate it into sales data
         String[] parameters= s.split(",");
         day= parameters[tempMap.get("Date")].split("-")[2];
         month= parameters[tempMap.get("Date")].split("-")[1];
         year = parameters[tempMap.get("Date")].split("-")[0];
         sku= parameters[tempMap.get("SKU")];
         price= Integer.parseInt(parameters[tempMap.get("Unit Price")]);
         quantity = Integer.parseInt(parameters[tempMap.get("Quantity")]);
         totalPrice= Integer.parseInt(parameters[tempMap.get("Total Price")]);
         Data data= new Data(day, month, year, sku, price, quantity, totalPrice);
         dataList.add(data);

      }


      return dataList;
    }

    public  List<String> readData()throws Exception{
      List<String> entryList= new ArrayList<>();
      File mobiuxDataFile = new File(address); // reading the file
      Scanner sc = new Scanner(mobiuxDataFile);
      
      
      while (sc.hasNextLine()) {
         
          
          entryList.add(sc.nextLine());
    
      }
     
    // entryList.remove(0);
      return entryList;
    }
}