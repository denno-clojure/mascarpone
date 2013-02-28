(use 'criterium.core)

; benchmark the rand function in clojure.core
(bench (rand) :verbose)
; WARNING: Final GC required 1.108927821207462 % of runtime
; x86_64 Mac OS X 10.8.2 2 cpu(s)
; Java HotSpot(TM) 64-Bit Server VM 24.0-b20
; Runtime arguments: -XX:+TieredCompilation -Dclojure.compile.path=/Users/Niko/projects/mascarpone/chapter03/target/classes -Dchapter03.version=0.1.0-SNAPSHOT -Dfile.encoding=UTF-8 -Dclojure.debug=false
; Evaluation count : 589802820 in 60 samples of 9830047 calls.
;       Execution time sample mean : 106.634558 ns
;              Execution time mean : 106.651574 ns
; Execution time sample std-deviation : 8.602397 ns
;     Execution time std-deviation : 8.685480 ns
;    Execution time lower quantile : 99.583451 ns ( 2.5%)
;    Execution time upper quantile : 131.072618 ns (97.5%)

; Found 8 outliers in 60 samples (13.3333 %)
; 	low-severe	 3 (5.0000 %)
; 	low-mild	 5 (8.3333 %)
;  Variance from outliers : 60.1735 % Variance is severely inflated by outliers
; nil

; if we need some progress indicators, 
; we just wrap thing around with-progress like this:
(with-progress-reporting 
	(bench (rand) :verbose))

(bench (rand) :reduce-with +)
; Evaluation count : 571211040 in 60 samples of 9520184 calls.
;              Execution time mean : 107.155630 ns
;     Execution time std-deviation : 2.739404 ns
;    Execution time lower quantile : 103.936962 ns ( 2.5%)
;    Execution time upper quantile : 112.141425 ns (97.5%)

; Found 1 outliers in 60 samples (1.6667 %)
; 	low-severe	 1 (1.6667 %)
;  Variance from outliers : 12.6250 % Variance is moderately inflated by outliers
; nil
