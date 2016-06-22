# The Computer Language Benchmarks Game
# http://benchmarksgame.alioth.debian.org
#
# contributed by Jesse Millikan
# Modified by Wesley Moxam
# Modified by Scott Leggett

def item_check(left, item, right)
    if left
        item + item_check(*left) - item_check(*right)
    else
        item
    end
end

def bottom_up_tree(item, depth)
    if depth > 0
        item_item = 2 * item
        depth -= 1
        [bottom_up_tree(item_item - 1, depth), item, bottom_up_tree(item_item, depth)]
    else
        [nil, item, nil]
    end
end

max_depth = ARGV[0].to_i
min_depth = 4

max_depth = [min_depth + 2, max_depth].max

stretch_depth = max_depth + 1
stretch_tree = bottom_up_tree(0, stretch_depth)

puts "stretch tree of depth #{stretch_depth}\t check: #{item_check(*stretch_tree)}"
stretch_tree = nil

long_lived_tree = bottom_up_tree(0, max_depth)

min_depth.step(max_depth, 2) do |depth|
  iterations = 2**(max_depth - depth + min_depth)

  check = 0

  (1..iterations).each do |i|
    check += item_check(*bottom_up_tree(i, depth))
    check += item_check(*bottom_up_tree(-i, depth))
  end

  puts "#{iterations * 2}\t trees of depth #{depth}\t check: #{check}"
end

puts "long lived tree of depth #{max_depth}\t check: #{item_check(*long_lived_tree)}"
