import UseScale from "./useScale";

function InjectUseScale(Component) {
    const injectedScale = function (props) {
        const scale = UseScale(props);
        return <Component {...props} scale={scale} />;
    };
    return injectedScale;
}

export default InjectUseScale;