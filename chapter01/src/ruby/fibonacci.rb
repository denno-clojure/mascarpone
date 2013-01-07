require 'matrix'

FIB_MATRIX = Matrix[[1,1],[1,0]]
def fib(n)
  (FIB_MATRIX**(n-1))[0,0]
end

if(ARGV[0])
	puts fib(ARGV[0].to_i)
else
	puts "Needs an integer to compute fibonacci result"
end