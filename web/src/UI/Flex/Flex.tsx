import React from 'react';
import styled from 'styled-components';

const SFlex = styled.div<FlexProps>`
  display: flex;
  flex: ${({ size }) => size};
  flex-direction: ${({ direction }) => direction};
  justify-content: ${({ justify }) => justify};
  align-items: ${({ align }) => align};
  min-width: ${({ minWidth }) => (typeof minWidth === 'number' ? `${minWidth}px` : minWidth)};
  min-height: ${({ minHeight }) => (typeof minHeight === 'number' ? `${minHeight}px` : minHeight)};
  max-width: ${({ maxWidth }) => (typeof maxWidth === 'number' ? `${maxWidth}px` : maxWidth)};
  max-height: ${({ maxHeight }) => (typeof maxHeight === 'number' ? `${maxHeight}px` : maxHeight)};
`;

export type FlexDirectionType = 'row' | 'row-reverse' | 'column' | 'column-reverse' | 'initial' | 'inherit';
export type FlexJustifyType = 'flex-start' | 'flex-end' | 'center' | 'space-between' | 'space-around' | 'space-evenly';
export type FlexAlignItemsType = 'stretch' | 'center' | 'flex-start' | 'flex-end' | 'baseline' | 'initial' | 'inherit';

export interface FlexProps {
  children?: React.ReactNode;
  size?: number;
  direction?: FlexDirectionType;
  justify?: FlexJustifyType;
  align?: FlexAlignItemsType;
  minWidth?: number;
  minHeight?: number;
  maxWidth?: number;
  maxHeight?: number;
}

export const Flex = ({
  children,
  direction,
  size = 1,
  justify,
  align,
  minWidth,
  minHeight,
  maxWidth,
  maxHeight,
}: FlexProps) => {
  return (
    <SFlex
      size={size}
      direction={direction}
      justify={justify}
      align={align}
      minWidth={minWidth}
      minHeight={minHeight}
      maxWidth={maxWidth}
      maxHeight={maxHeight}
    >
      {children}
    </SFlex>
  );
};
