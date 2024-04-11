    package com.careerit.jfs.oops.InterfaceExamples;

    import java.util.Scanner;

    interface Payment{

        public String pay(String toAccount,String fromAccount,double amount);
    }

    class CreditcardPayment implements Payment{
        @Override
        public String pay(String toAccount,String fromAccount,double amount){
            return "CC To account :"+toAccount+"from account :"+fromAccount+" amount"+amount;
        }
    }

    class CashPayment implements Payment{
        @Override
        public String pay(String toAccount,String fromAccount,double amount){
            return "To account :"+toAccount+"from account :"+fromAccount+" amount"+amount;
        }
    }

    class NetBankingPayment implements Payment{
        @Override
        public String pay(String toAccount,String fromAccount,double amount){
            return "To account :"+toAccount+"from account :"+fromAccount+" amount"+amount;
        }
    }

    class PaymentService{
        private Payment payment;
        public PaymentService(Payment payment){
            this.payment=payment;
        }
        public String payBill(String toAccount,String fromAccount,double amount){
            return payment.pay(toAccount,fromAccount,amount);
        }
    }
    public class PaymentManager {
        public static void main(String[] args) {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter Payment method (CC/Cash/NB)");
            String pm= sc.nextLine();
            Payment payment=getPayment(pm);
            PaymentService service=new PaymentService(payment);
            String response=service.payBill("123","321",1000);
            System.out.println(response);
        }
        public static Payment getPayment(String type) {

            if(type.equals("CC")){
                return new CreditcardPayment();
            }
            else if(type.equals("Cash")){
                return new CreditcardPayment();
            }
            else if(type.equals("NB")){
                return new CreditcardPayment();
            }
            else{
                throw new IllegalArgumentException(" NA ");
            }

        }
    }

