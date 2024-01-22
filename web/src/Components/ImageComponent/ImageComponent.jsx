import React from "react";
import "./ImageComponent.css";

export default class ImageComponent extends React.Component {
  state = { isOpen: true };

  handleShowDialog = (closeModal) => {
    this.setState({ isOpen: !this.state.isOpen });
    };

  componentDidUpdate(prevProps) {
    
    if (this.props.base64Image !== prevProps.base64Image) {
      this.setState({ isOpen: true });
    }
  }

  render() {
    const { base64Image } = this.props;
    const { isOpen } = this.state;

    return (
      <div>
        {isOpen && (
          <dialog
            className="dialog"
            style={{ position: "absolute" }}
            open
            onClick={this.handleShowDialog}
          >
            <img
              className="image"
              src={base64Image}
              onClick={this.handleShowDialog}
              alt="no image"
            />
          </dialog>
        )}
      </div>
    );
  }
}
