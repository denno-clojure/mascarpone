{:tutorial {:uuid "/Users/niko/.vmfest/models/vmfest-tutorial.vdi", :cpu-count 1, :network [{:host-only-interface
	"vboxnet0", :attachment-type :host-only} {:attachment-type :nat}], :storage [{:devices [nil nil {:device-type :dvd}
	nil], :name "IDE Controller", :bus :ide}], :boot-mount-point ["IDE Controller" 0], :memory-size 512}, :lubuntu
	{:image-id "lubuntu", :image-name "lubuntu", :uuid "/Users/niko/.vmfest/models/vmfest-lubuntu.vdi", :cpu-count 1,
	:network [{:host-only-interface "vboxnet0", :attachment-type :host-only} {:attachment-type :nat}], :storage [{:devices
		[nil nil {:device-type :dvd} nil], :name "IDE Controller", :bus :ide}], :boot-mount-point ["IDE Controller" 0],
		:memory-size 512}, :nico {:image-id "nico", :image-name "nico", :uuid "/Users/niko/.vmfest/models/vmfest-nico.vdi",
		:cpu-count 1, :network [{:host-only-interface "vboxnet0", :attachment-type :host-only} {:attachment-type :nat}],
		:storage [{:devices [nil nil {:device-type :dvd} nil], :name "IDE Controller", :bus :ide}], :boot-mount-point ["IDE
		Controller" 0], :memory-size 512}}