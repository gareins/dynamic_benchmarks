public class bubble 
{
    public static int[] random(int n)
    {
        int[] lst = new int[n];
        long x = 0;
        for(int i = 0; i < n; i++)
        {
            x = (140671485 * x + 12820163) % 16777216;
            lst[i] = (int) x;
        }
        return lst;
    }
    
    public static int[] bubble_fun(int[] lst)
    {
        while(true)
        {
            boolean swapped = false;
            for(int i = 1; i < lst.length; i++)
                if(lst[i] < lst[i - 1])
                {
                    int tmp = lst[i];
                    lst[i] = lst[i - 1];
                    lst[i - 1] = tmp;
                    swapped = true;
                }
                
            if(!swapped)
                break;
        }
        return lst;
    }
    
    public static void main(String[] args)
    {        
        int[] lst = random(Integer.parseInt(args[0]));
        lst = bubble_fun(lst);
        for(int i: lst)
            System.out.print(i + " ");
        System.out.println("");
    }
}
