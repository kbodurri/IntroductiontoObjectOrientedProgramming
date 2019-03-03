/* ComplexNumber.java
 *
 * A class that implements a complex number, consisting of
 * a double real and a double imaginary part
 * Supported operations: addition, subtraction, multiplication
 *
 * Contributors : Klajdi Bodurri and Eirini Tsitsopoulou.
 */

public class ComplexNumber {
  double real;
  double imag;

  // Α constructor that creates a complex number
  public ComplexNumber (double initReal, double initImag){
    real = initReal;
    imag = initImag;
  }

  // Set/get value methods
  public void setReal(double newReal){
    real = newReal;
  }
  public void setImag(double newImag){
    imag = newImag;
  }
  public double getReal(){
    return real;
  }
  public double getImag(){
    return imag;
  }

  // Add num to ComplexNumber and assign result to new ComplexNumber
  public ComplexNumber add(ComplexNumber num){
    ComplexNumber res;
    double res_real = this.getReal() + num.getReal();
    double res_imag = this.getImag() + num.getImag();
    res = new ComplexNumber(res_real, res_imag);
    return res;
  }

  // Subtract num from ComplexNumber and assign result to new ComplexNumber
  public ComplexNumber subtract(ComplexNumber num){
    ComplexNumber res;
    double res_real = this.getReal() - num.getReal();
    double res_imag = this.getImag() - num.getImag();
    res = new ComplexNumber(res_real, res_imag);
    return res;
  }

  // Multiply num by ComplexNumber and assign result to new ComplexNumber
  ComplexNumber multiply(ComplexNumber num){
    ComplexNumber res;
    double res_real = this.getReal()*num.getReal() - this.getImag()*num.getImag();
    double res_imag = this.getReal()*num.getImag() + this.getImag()*num.getReal();
    res = new ComplexNumber(res_real, res_imag);
    return res;
  }

  // Print ComplexNumber in format: Re +- |Im|j
  public String toString(){
    String str1 = String.format("%.2f", this.getReal());
    String sign = (this.getImag() >= 0) ? " + " : " - " ;
    String str2 = String.format("%.2f", Math.abs(this.getImag()));

    String str = str1 + sign + str2 + "j";

    return str;
  }
}
