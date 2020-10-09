# reference:
http://www.ruanyifeng.com/blog/2015/07/flex-grammar.html 

# 以下6个属性设置在容器上
flex-direction: row | row-reverse | column | column-reverse;
flex-wrap: nowrap | wrap | wrap-reverse;
flex-flow: <flex-direction> || <flex-wrap>;

# justify-content属性定义了项目在主轴上的对齐方式。
justify-content: flex-start | flex-end | center | space-between | space-around;

# align-items属性定义项目在交叉轴上如何对齐。
align-items: flex-start | flex-end | center | baseline | stretch;

# align-content属性定义了多根轴线的对齐方式。如果项目只有一根轴线，该属性不起作用。
align-content: flex-start | flex-end | center | space-between | space-around | stretch;

# 以下6个属性设置在项目上。
# order属性定义项目的排列顺序。数值越小，排列越靠前，默认为0。
 order: <integer>;

# flex-grow属性定义项目的放大比例，默认为0，即如果存在剩余空间，也不放大。
flex-grow: <number>; /* default 0 */

# flex-shrink属性定义了项目的缩小比例，默认为1，即如果空间不足，该项目将缩小。
flex-shrink: <number>; /* default 1 */

# flex-basis属性定义了在分配多余空间之前，项目占据的主轴空间（main size）。浏览器根据这个属性，计算主轴是否有多余空间。它的默认值为auto，即项目的本来大小。
flex-basis: <length> | auto; /* default auto */

# flex属性是flex-grow, flex-shrink 和 flex-basis的简写，默认值为0 1 auto。后两个属性可选。
flex: none | [ <'flex-grow'> <'flex-shrink'>? || <'flex-basis'> ]

# align-self属性允许单个项目有与其他项目不一样的对齐方式，可覆盖align-items属性。默认值为auto，表示继承父元素的align-items属性，如果没有父元素，则等同于stretch。
align-self: auto | flex-start | flex-end | center | baseline | stretch;
