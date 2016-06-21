local function fib(n)
    if n == 0 then 
        return 0;
    elseif n == 1 then
        return 1;
    else
        return fib(n - 1) + fib(n - 2);
    end
end

local n = tonumber(arg and arg[1]);
io.write(string.format("%d\n", fib(n)));
