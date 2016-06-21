local function random(n)
    local lst = {};
    local x = 0;
    for i = 0, n - 1, 1 do
        x = (140671485 * x + 12820163) % 16777216;
        lst[i] = x;
    end
    return lst;
end

local function bubble(n)
    local lst = random(n);
    while true do
	local swapped = false;
	for i=1, n - 1, 1 do
	    if lst[i] < lst[i - 1] then
	        local tmp = lst[i];
		lst[i] = lst[i - 1];
		lst[i - 1] = tmp;
		swapped = true;
	    end
	end

	if not swapped then
	    break
        end
    end
    return lst;
end


local n = tonumber(arg and arg[1]);
local lst = bubble(n);

local str = "";
for i=0, n - 1, 1 do
    str = str .. lst[i] .. " ";
end

print(str);
