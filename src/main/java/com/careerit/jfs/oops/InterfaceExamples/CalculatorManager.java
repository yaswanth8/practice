    package com.careerit.jfs.oops.InterfaceExamples;


    interface Calculator{
        public  double convert(double p,double t,double r);
    }
    class SimpleInterestCalc implements Calculator{

        @Override
        public double convert(double p, double t, double r) {
            return (p*t*r)/100;
        }
    }

    class EMIInterestCalc implements Calculator{

        @Override
        public double convert(double p, double t, double r) {
            r=r/12/100;
            return (p*t*r)/100;

        }
    }
    public class CalculatorManager {

        public static void main(String[] args) {

            Calculator col=calcu(1);
            col.convert(1,2,3);
        }
        public static Calculator calcu(int cal){
             if(cal==1) return new SimpleInterestCalc();
             else return new EMIInterestCalc();
        }

    }
