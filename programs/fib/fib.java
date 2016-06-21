public class fib 
{
    public static int fib_rec(int n)
    {
        if(n == 0) return 0;
        else if(n == 1) return 1;
        else return fib_rec(n - 1) + fib_rec(n - 2);
    }
    
    public static void main(String[] args)
    {
        System.out.println(fib_rec(Integer.parseInt(args[0])));
    }
}
