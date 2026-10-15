USE ecommerce;

INSERT INTO product
    (product_id, name, image, score, supplier_id, price, stock, total_score, number_of_reviews)
VALUES
    (1, 'POCO',
     'https://storage.comprasmartphone.com/smartphones/poco-x8-pro-max.png',
     5.0, 1, 399.99, 25, 50, 10),

    (2, 'Samsung',
     'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcRH0UnMRl1WZRiy9egq3KHLcDYqCg5mpwpZvFDTkZCN2yeB2NSZpgWvY6cS5nxua2aQ5UvZCNCUbUNwQlVwntudTsmAZBS1HrxEtqdeh7j8fSlKdUoiBWEq4FjXzokkJnXGubqm_w&usqp=CAc',
     3.4, 1, 699.99, 18, 34, 10),

    (3, 'Apple',
     'https://www.apple.com/euro/iphone/home/d/screens_alt/images/meta/iphone__bh930eyjnj0i_og.png',
     2.5, 1, 999.99, 12, 25, 10)