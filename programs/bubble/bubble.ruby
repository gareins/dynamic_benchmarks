def random(n)
    lst = {};
    x = 0;
    for i in 0..n-1
        x = (140671485 * x + 12820163) % 16777216;
        lst[i] = x;
    end
    return lst;
end

def bubble(n)
    lst = random(n);
    while true
        swapped = false;
        for i in 1..n-1
            if lst[i] < lst[i - 1]
                tmp = lst[i];
                lst[i] = lst[i - 1];
                lst[i - 1] = tmp;
                swapped = true;
            end
        end

        if not swapped
            break
        end
    end
    return lst;
end


n = (ARGV[0] || 1).to_i
lst = bubble(n) 

for i in 0..n-1
    printf "%d ", lst[i]
end
printf "\n"

