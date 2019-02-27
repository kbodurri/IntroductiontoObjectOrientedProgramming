public class ComplexNumberMain{
  public static void main(String[] args){
    ComplexNumber cnum1, cnum2, cnum3;
    double nReal, nImag;

    cnum1 = new ComplexNumber(2, 3.4);
    cnum2 = new ComplexNumber(1.5, 8);

    cnum3 = cnum1.add(cnum2);

    nReal = cnum3.getReal();
    nImag = cnum3.getImag();

    System.out.println("real is " +nReal+ " imag is " +nImag);
    System.out.println("toString output: " + cnum3);

    cnum3 = cnum1.subtract(cnum2);

    nReal = cnum3.getReal();
    nImag = cnum3.getImag();

    System.out.println("real is " +nReal+ " imag is " +nImag);
    System.out.println("toString output: " + cnum3);

    cnum3 = cnum1.multiply(cnum2);

    nReal = cnum3.getReal();
    nImag = cnum3.getImag();

    System.out.println("real is " +nReal+ " imag is " +nImag);
    System.out.println("toString output: " + cnum3);

  }
}
