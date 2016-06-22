def fib(n)
    if n == 0 then 
        return 0;
    elsif n == 1 then
        return 1;
    else
        return fib(n - 1) + fib(n - 2);
    end
end

n = (ARGV[0] || 1).to_i
printf "%d\n", fib(n)
