var CART = {
		itemNumChange : function(){
			
			$(".increment").click(function(){// ＋
			
				var _thisInput = $(this).siblings("input");
				_thisInput.val(eval(_thisInput.val()) + 1);
				$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
					//
					
					
					 CART.refreshTotalPrice();
					 CART.refreshItemPrice(_thisInput,_thisInput.attr("itemprice"),_thisInput.val());
				});
			});
			$(".decrement").click(function(){// -
				var _thisInput = $(this).siblings("input");
				if(eval(_thisInput.val()) == 1){
					return ;
				}
				_thisInput.val(eval(_thisInput.val()) - 1);
				$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
					CART.refreshTotalPrice();
					CART.refreshItemPrice(_thisInput,_thisInput.attr("itemprice"),_thisInput.val());
				});
			});
			/*
			 * $(".itemnum").change(function(){ var _thisInput = $(this);
			 * $.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
			 * CART.refreshTotalPrice(); }); });
			 */
		},
		refreshItemPrice:function(d,p,n){
			
		
	var dd=	$(d).parent().parent().nextAll("div[class='pItem pSubtotal']").children("[id='total_price']");
			var privce=p/100*n;
			
			
			$(dd).html(new Number(privce).toFixed(2)).priceFormat({ // 价格格式化插件
				 prefix: '¥',
				 thousandsSeparator: ',',
				 centsLimit: 2
			});
			
		},
		refreshTotalPrice : function(){ // 重新计算总价
			var total = 0;
			$(".itemnum").each(function(i,e){
				var _this = $(e);
				total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
			});
			$("#allMoney2").html(new Number(total/100).toFixed(2)).priceFormat({ // 价格格式化插件
				 prefix: '¥',
				 thousandsSeparator: ',',
				 centsLimit: 2
			});
		}
	};

	$(function(){
		CART.itemNumChange();
	});