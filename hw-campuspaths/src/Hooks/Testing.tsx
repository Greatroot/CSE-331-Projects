import UsePan from "./usePan";
import UseScale from "./useScale";
import {useRef} from "react";


// Testing my scale hook.
// export const UseScaleExample = () => {
//     const ref = useRef<HTMLDivElement | null>(null)
//     const scale = useScale(ref)
//     return (
//         <div ref={ref}>
//             <span>{scale}</span>
//         </div>
//     )
// }
//
// export default UseScaleExample;


// Testing my pan hook.
// const UsePanExample = () => {
//     const [offset, startPan] = usePan()
//
//     return (
//         <div onMouseDown={startPan}>
//             <span>{JSON.stringify(offset)}</span>
//         </div>
//     )
// }
//
// export default UsePanExample;